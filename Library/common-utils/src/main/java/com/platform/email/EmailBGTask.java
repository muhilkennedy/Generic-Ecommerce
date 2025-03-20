package com.platform.email;

import java.io.File;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.bgwork.BGJob;
import com.platform.exceptions.BGWorkException;
import com.platform.serviceImpl.EmailServiceImpl;
import com.platform.util.EmailUtil;

import jakarta.mail.MessagingException;

/**
 * @author muhil
 */
@Component //we can persist some data if required in jobdata map for subsequent executions
//@DisallowConcurrentExecution
public class EmailBGTask extends BGJob {
	
	@Autowired
	private EmailServiceImpl emailService;

	@Override
	public void schedule() throws SchedulerException {
		// No-OP
	}

	@Override
	public void run(JobExecutionContext context) throws BGWorkException {
		JobDataMap map = context.getJobDetail().getJobDataMap();
		try {
			emailService.sendMultiMediaEmail(map.getString(EmailUtil.EMAIL_TO), map.getString(EmailUtil.EMAIL_SUBJECT),
					map.getString(EmailUtil.EMAIL_MESSAGE), (List<File>) map.get(EmailUtil.EMAIL_ATTACHMENT));
		} catch (MessagingException e) {
			throw new BGWorkException(e);
		}
	}

}
