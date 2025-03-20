package com.platform.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author muhil
 */
@Component
@ConfigurationProperties(prefix = "encryption.db")
public class DbSecretConfigurartion {
	
	private String secret;
	private String initVector;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getInitVector() {
		return initVector;
	}

	public void setInitVector(String initVector) {
		this.initVector = initVector;
	}
	
}
