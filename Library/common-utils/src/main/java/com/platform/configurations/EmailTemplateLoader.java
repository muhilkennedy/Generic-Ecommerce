package com.platform.configurations;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.logging.Log;
import com.platform.model.EmailTemplateInfo;

import jakarta.annotation.PostConstruct;

/**
 * @author muhil NOTE: load template files from extending projects. Follow
 *         convention -> email-templates/email-template-<module_name>.json
 */
@Configuration
public class EmailTemplateLoader {

	private List<EmailTemplateInfo> templateInfos = new ArrayList<EmailTemplateInfo>();

	@PostConstruct
	public void loadTemplates() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath*:email-templates/email-template-*.json");
			for (Resource resource : resources) {
				Log.platform.info("Email Templates Loading from resource: {}", resource.getURI());
				InputStream inputStream = resource.getInputStream();
				templateInfos.addAll(objectMapper.readValue(inputStream, new TypeReference<List<EmailTemplateInfo>>() {
				}));
			}
			Log.platform.info("{} Email Template files Loaded ", templateInfos.size());
		} catch (IOException e) {
			throw new RuntimeException("Failed to load templates.json", e);
		}
	}

	public List<EmailTemplateInfo> getTemplateInfos() {
		return templateInfos;
	}

}
