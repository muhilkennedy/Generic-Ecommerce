package com.platform.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.platform.configurations.ConfigTypes;
import com.platform.entity.ConfigType;
import com.platform.server.BaseSession;
import com.platform.service.ConfigTypeService;

/**
 * @author Muhil
 * configs to be read from implementing application.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfiguration {
	
	private String host;
	private int port;
	private String username;
	private String password;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean smtpAuth;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private boolean startTls;

	@Value("${spring.mail.transport.protocol}")
	private String protocol;
	
	@Autowired
	private ConfigTypeService configService;
	
	@Autowired
	private EmailCacheService cacheService;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public boolean isStartTls() {
		return startTls;
	}

	public void setStartTls(boolean startTls) {
		this.startTls = startTls;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public JavaMailSender getDefaultMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.starttls.enable", startTls);
		props.put("mail.transport.protocol", protocol);
		return mailSender;
	}

	/**
	 * @return JavaMailSender based on tenant email config.
	 */
	public JavaMailSender getMailSender() {
		if (BaseSession.getTenant() == null) {
			return getDefaultMailSender();
		} else {
			if (cacheService.get(BaseSession.getTenantUniqueName()) == null) {
				List<ConfigType> configs = configService.findConfigsByType(ConfigTypes.EMAIL.name());
				if (configs.isEmpty()) {
					return getDefaultMailSender();
				} else {
					Map<String, String> configMap = generateEmailConfigMap(configs);
					JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
					mailSender.setHost(configMap.get(EmailConfigTypes.HOST.name()));
					mailSender.setPort(Integer.parseInt(configMap.get(EmailConfigTypes.PORT.name())));
					mailSender.setUsername(configMap.get(EmailConfigTypes.EMAILID.name()));
					mailSender.setPassword(configMap.get(EmailConfigTypes.PASSWORD.name()));
					Properties props = mailSender.getJavaMailProperties();
					props.put("mail.smtp.auth", smtpAuth);
					props.put("mail.smtp.starttls.enable", startTls);
					props.put("mail.transport.protocol", protocol);
					cacheService.add(BaseSession.getTenantUniqueName(), mailSender);
				}
			}
			return (JavaMailSender) cacheService.get(BaseSession.getTenantUniqueName());
		}
	}

	private Map<String, String> generateEmailConfigMap(List<ConfigType> configs) {
		Map<String, String> configMap = new HashMap<String, String>();
		configs.stream().forEach(config -> configMap.put(config.getName(), config.getValue()));
		return configMap;
	}


}
