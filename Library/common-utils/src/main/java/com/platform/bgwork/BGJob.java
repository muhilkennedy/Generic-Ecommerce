package com.platform.bgwork;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.platform.entity.BaseEntity;
import com.platform.exceptions.BGWorkException;
import com.platform.logging.AuditOperation;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.platform.service.AuditService;
import com.platform.service.BaseService;
import com.platform.util.PlatformUtil;

/**
 * @author muhil.
 * NOTE: make sure to call session setup and tearup
 * executed via scheduled job.
 */
@Component
@PersistJobDataAfterExecution //we can persist some data if required in jobdata map for subsequent executions
public abstract class BGJob implements Job {

	@Autowired
	@Qualifier("TenantService")
	private BaseService baseTenantService;

	@Autowired
	private Scheduler quartzScheduler;
	
	@Autowired
	protected AuditService auditService;
	
	@Autowired
	protected BGWorkScheduler jobScheduler;
	
	/**
	 * @param event Execute after application startup to schedule the default jobs.
	 * @throws SchedulerException
	 */
	@EventListener
	private void onApplicationEvent(ApplicationReadyEvent event) throws SchedulerException {
		schedule();
	}

	/**
	 * Schedule the job using BGWorkScheduler (jobScheduler)
	 * 
	 * @throws SchedulerException
	 */
	public abstract void schedule() throws SchedulerException;
	
	/**
	 * method from Job class which will be triggered by the quartz scheduler.
	 * Override in tasks class as needed.
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail detail = context.getJobDetail();
		if (detail != null && detail.getJobDataMap() != null) {
			try {
				if ((Boolean) detail.getJobDataMap().get(PlatformUtil.RUN_FOR_ALL_PARAM)) {
					runForAllTenants(context);
				} 
				else {
					try {
						BaseSession.setupSession(detail.getJobDataMap().getLong(PlatformUtil.TENANT_PARAM),
								detail.getJobDataMap().getLong(PlatformUtil.USER_PARAM));
						run(context);
					} catch (BGWorkException e) {
						Log.platform.error("BGWork failed: {}", e);
						auditService.logAudit(BaseSession.getTenant(), BaseSession.getUser(), e.getMessage(), AuditOperation.BGWORK, this);
						throw e;
					} finally {
						teardownSession();
					}
				}
			} catch (BGWorkException e) {
				throw new JobExecutionException(e);
			}
		} else {
			throw new JobExecutionException("Job Details not found for the job");
		}
	}

	public void schedule(JobDetail jobdetail, Trigger trigger) throws SchedulerException {
		quartzScheduler.scheduleJob(jobdetail, trigger);
	}

	/**
	 * Actual work to be executed. (business logic)
	 */
	public abstract void run(JobExecutionContext context) throws BGWorkException;

	public void runForAllTenants(JobExecutionContext context) throws BGWorkException {
		//Assumption: 100 tenants to be max, mostly we will never touch this limit
		for(Object tenant : baseTenantService.findAll()) {
			Log.platform.info("Executing BGWork {} for tenant : {}");
			try {
				setupSession((BaseEntity) tenant);
				run(context);
			} catch (BGWorkException e) {
				String msg = String.format("Error running job for tenant : {%s} : {%s}", tenant, e);
				Log.platform.error(msg);
				auditService.logAudit(BaseSession.getTenant(), BaseSession.getUser(), msg, AuditOperation.BGWORK, this);
				throw e;
			}
			finally {
				teardownSession();
			}
		}
	}

	protected String getJobId(Class<?> cls) {
		String jobId = cls.getSimpleName();
		Log.tenant.debug("Registering BG job with Id {} for {}", jobId, cls.getCanonicalName());
		return jobId;
	}

	protected void setupSession(BaseEntity tenant) {
		BaseSession.setCurrentTenant(tenant);
	}

	protected void setupSession(Long tenantId, Long userId) {
		BaseSession.setupSession(tenantId, userId);
	}

	protected void teardownSession() {
		BaseSession.tearDownSession();
	}

}
