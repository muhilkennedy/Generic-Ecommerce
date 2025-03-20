package com.platform.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author muhil
 */
@Component
@ConfigurationProperties(prefix = "encryption.file")
public class FileSecretConfiguration {
	
	private String secret;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
