package com.platform.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author muhil
 */
@Component
@ConfigurationProperties(prefix = "encryption.jwt")
public class JwtSecretConfiguration {

	private String secret;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
