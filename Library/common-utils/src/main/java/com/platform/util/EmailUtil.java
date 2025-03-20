package com.platform.util;

import java.io.File;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.bgwork.BGWorkScheduler;
import com.platform.email.EmailBGTask;
import com.platform.logging.Log;
import com.platform.server.BaseSession;

/**
 *@author Muhil 
 */
@Component
public class EmailUtil {
	
	private static BGWorkScheduler bgScheduler;
	public static final String EMAIL_JOB_GROUP = "EMAIL";
	public static final String EMAIL_TO = "TO";
	public static final String EMAIL_SUBJECT = "SUBJECT";
	public static final String EMAIL_MESSAGE = "MESSAGE";
	public static final String EMAIL_ATTACHMENT = "ATTACHMENT";
	
	@Autowired
	public void setBgScheduler(BGWorkScheduler bgScheduler) {
		EmailUtil.bgScheduler = bgScheduler;
	}

	public static void sendEmail(String to, String subject, String message) {
		sendMultiMediaEmail(to, subject, message, null);
	}

	public static void sendMultiMediaEmail(String to, String subject, String message, List<File> attachments) {
		JobDataMap jobData = new JobDataMap();
		jobData.put(PlatformUtil.TENANT_PARAM, BaseSession.getTenantId());
		jobData.put(PlatformUtil.USER_PARAM, BaseSession.getUserId());
		try {
			jobData.put(EMAIL_TO, to);
			jobData.put(EMAIL_SUBJECT, subject);
			jobData.put(EMAIL_MESSAGE, message);
			jobData.put(EMAIL_ATTACHMENT, attachments);
			bgScheduler.fireAndForget(EmailBGTask.class, jobData, EMAIL_JOB_GROUP);
		} catch (SchedulerException e) {
			Log.tenant.error("Exception scheduling Email Task : ", e);
		}
		
	}
	
}
