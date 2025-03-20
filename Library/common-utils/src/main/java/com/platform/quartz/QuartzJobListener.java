package com.platform.quartz;

import java.util.UUID;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.bgwork.ScheduledTaskStatus;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.platform.serviceImpl.QuartzServiceImpl;
import com.platform.util.PlatformUtil;


/**
 * @author Muhil
 * This listener will be invoked before and after job trigger everytime.
 * we make us of this to persist job state on our internal table.
 */
@Component
public class QuartzJobListener extends JobListenerSupport {
	
	@Autowired
	private QuartzServiceImpl quartzSerice;

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		Log.platform.info("QuartzJobListener : Starting to execute job : {}", context.toString());
		updateJobStatus(context.getJobDetail(), ScheduledTaskStatus.INPROGRESS.name(), null);
		super.jobToBeExecuted(context);
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		super.jobWasExecuted(context, jobException);
		Log.platform.info("QuartzJobListener : Completed executing job : {}", context.toString());
		if (jobException != null) {
			Log.platform.error("QuartzJobListener : Failed to execute job : {} with error : {}", context.getJobDetail(), jobException);
			updateJobStatus(context.getJobDetail(), ScheduledTaskStatus.FAILED.name(),
					jobException.getMessage());
			return;
		}
		updateJobStatus(context.getJobDetail(), ScheduledTaskStatus.SUCCESS.name(), null);
	}

	private void updateJobStatus(JobDetail jobDetail, String status, String errorMsg) {
		JobDataMap map = jobDetail.getJobDataMap();
		// we need to do this to identify unique records in history for updating status
		if (!map.containsKey(PlatformUtil.KEY_UUID)) {
			map.put(PlatformUtil.KEY_UUID, UUID.randomUUID().toString());
		}
		BaseSession.setupSession(map.getLong(PlatformUtil.TENANT_PARAM), map.getLong(PlatformUtil.USER_PARAM));
		quartzSerice = ApplicationContextHolder.getContext().getBean(QuartzServiceImpl.class);
		quartzSerice.createOrUpdateQuartzHistory(map.getString(PlatformUtil.KEY_UUID), jobDetail.getKey().getName(),
				jobDetail.getKey().getGroup(), status, errorMsg);
		BaseSession.tearDownSession();
	}

}
