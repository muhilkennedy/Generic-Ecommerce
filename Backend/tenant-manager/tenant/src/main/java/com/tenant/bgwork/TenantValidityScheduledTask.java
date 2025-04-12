package com.tenant.bgwork;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.bgwork.BGJob;
import com.platform.exceptions.BGWorkException;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.tenant.service.TenantService;

/**
 * @author Muhil 
 */
@Component
@DisallowConcurrentExecution
public class TenantValidityScheduledTask extends BGJob {
	
	@Autowired
	private TenantService tenantService;

	@Override
	public void schedule() throws SchedulerException {
//		jobScheduler.scheduleCronJob(getClass().getCanonicalName(), TenantValidityScheduledTask.class
//				, "0/5 * * * * ?", true, true);
	}

	@Override
	public void run(JobExecutionContext context) throws BGWorkException {
		Log.tenant.info("Executing Tenant validity check for tenant : {}", BaseSession.getTenantUniqueName());
		
	}

}
