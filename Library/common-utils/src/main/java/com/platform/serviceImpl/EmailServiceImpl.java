package com.platform.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.platform.configurations.EmailTemplateLoader;
import com.platform.email.EmailConfiguration;
import com.platform.entity.EmailTemplate;
import com.platform.logging.Log;
import com.platform.model.EmailTemplateInfo;
import com.platform.repository.EmailTemplateRepository;
import com.platform.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * @author Muhil
 */
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailConfiguration emailConfig;
	
	@Autowired
	private EmailTemplateRepository templateRepository;
	
	@Autowired
	private EmailTemplateLoader templateLoader;

	@Override
	public void sendSimpleTextEmail(String to, String subject, String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setFrom("noreplyeventemail@gmail.com");
		emailConfig.getMailSender().send(mailMessage);
	}

	@Override
	public void sendMultiMediaEmail(String to, String subject, String htmlContent) throws MessagingException {
		sendMultiMediaEmail(to, subject, htmlContent, null);
	}

	@Override
	public void sendMultiMediaEmail(String to, String subject, String htmlContent, List<File> attachments)
			throws MessagingException {
		MimeMessage message = emailConfig.getMailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlContent, true);
		helper.setFrom("noreplyeventemail@gmail.com");
		CollectionUtils.emptyIfNull(attachments).stream().forEach(attachment -> {
			try {
				helper.addAttachment(attachment.getName(), attachment);
			} catch (MessagingException e) {
				Log.platform.error("Exception while adding attachment ", e);
				throw new RuntimeException(e);
			}
		});
		emailConfig.getMailSender().send(message);
	}
	
	@Override
	public EmailTemplate createOrUpdateEmailTemplate(String title, File templateFile) throws IOException {
		EmailTemplate template = findEmailTemplateByTitle(title);
		if (template == null) {
			template = new EmailTemplate();
			template.setTitle(title);
		}
		template.setContent(FileUtils.readFileToByteArray(templateFile));
		return templateRepository.save(template);
	}

	@Override
	public EmailTemplate findEmailTemplateByTitle(String title) {
		return templateRepository.findTemplateByName(title);
	}
	
	@Override
	public List<EmailTemplateInfo> getAvailableEmailTemplateInfos(){
		return templateLoader.getTemplateInfos();
	}

}
