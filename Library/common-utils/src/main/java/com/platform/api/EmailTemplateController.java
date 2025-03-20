package com.platform.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.entity.EmailTemplate;
import com.platform.messages.GenericResponse;
import com.platform.model.EmailTemplateInfo;
import com.platform.service.EmailService;
import com.platform.user.permissions.Permissions;
import com.platform.util.FileUtil;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("admin/emailtemplate")
@ValidateUserToken
public class EmailTemplateController {

	@Autowired
	private EmailService emailService;
	
	@UserPermission(values = { Permissions.MANAGE_PROMOTIONS })
	@GetMapping
	public GenericResponse<EmailTemplateInfo> getTemplateInfos() throws IOException {
		return new GenericResponse<EmailTemplateInfo>(emailService.getAvailableEmailTemplateInfos());
	}

	@UserPermission(values = { Permissions.MANAGE_PROMOTIONS })
	@PostMapping(value = "/{title}")
	public GenericResponse<EmailTemplate> createOrUpdateEmailTemplate(@PathVariable String title,
			@RequestParam("file") MultipartFile file) throws IOException {
		return new GenericResponse<EmailTemplate>(
				emailService.createOrUpdateEmailTemplate(title, FileUtil.generateFileFromMutipartFile(file)));
	}

}
