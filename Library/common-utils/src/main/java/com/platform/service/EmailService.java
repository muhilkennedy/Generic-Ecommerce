package com.platform.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.platform.entity.EmailTemplate;
import com.platform.model.EmailTemplateInfo;

import jakarta.mail.MessagingException;

/**
 * @author Muhil
 */
public interface EmailService {

	public void sendSimpleTextEmail(String to, String subject, String message);

	public void sendMultiMediaEmail(String to, String subject, String htmlContent) throws MessagingException;

	public void sendMultiMediaEmail(String to, String subject, String htmlContent, List<File> attachments)
			throws MessagingException;

	public EmailTemplate createOrUpdateEmailTemplate(String title, File template) throws IOException;

	EmailTemplate findEmailTemplateByTitle(String title);

	List<EmailTemplateInfo> getAvailableEmailTemplateInfos();

}
