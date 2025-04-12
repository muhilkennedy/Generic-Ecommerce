package com.platform.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Muhil
 */
@Component
public class PropertiesUtil {

	private static Environment environment;

	@Autowired
	public void setEnvironment(Environment env) {
		PropertiesUtil.environment = env;
	}

	public static String getStringProperty(String key) {
		return environment.getProperty(key);
	}

	public static Boolean getBooleanProperty(String key) {
		return Boolean.parseBoolean(environment.getProperty(key));
	}

	public static Long getLongProperty(String key) {
		return Long.parseLong(environment.getProperty(key));
	}

	public static Integer getIntProperty(String key) {
		return Integer.parseInt(environment.getProperty(key));
	}
	
	/**
	 * HELPER METHODS
	 * */
	
	public static String getTenantMSBaseUrl() {
		return getStringProperty("platform.ms.tenant-manager");
	}
	
	public static String getUserMSBaseUrl() {
		return getTenantMSBaseUrl();
	}
	
	public static String getProductMSBaseUrl() {
		return getStringProperty("platform.ms.product-manager");
	}

}
