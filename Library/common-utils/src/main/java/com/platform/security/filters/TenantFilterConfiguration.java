package com.platform.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.logging.Log;

/**
 * @author Muhil
 *
 */
@Configuration
public class TenantFilterConfiguration {
	
	@Autowired
	private TenantValidationFilter filter;

	@Bean
	public FilterRegistrationBean<TenantValidationFilter> tenantFilterRegistration() {
		Log.platform.info("----- User Filter Registrarion -----");
		FilterRegistrationBean<TenantValidationFilter> registration = new FilterRegistrationBean<TenantValidationFilter>();
		registration.setFilter(filter);
		// Validate for all incoming request
		registration.addUrlPatterns("*");
		return registration;
	}
}
