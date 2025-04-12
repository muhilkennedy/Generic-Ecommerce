package com.platform.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.logging.Log;
import com.platform.util.FilterUtil;

/**
 * @author Muhil
 *
 */
@Configuration
public class FilterConfiguration {
	
	@Autowired
	private TenantValidationFilter filter;
	
	@Autowired
	private UserTokenSecurityFilter userFilter;
	
	@Autowired
	private SubnetFilter subnetFilter;

	@Bean
	public FilterRegistrationBean<TenantValidationFilter> tenantFilterRegistration() {
		Log.platform.info("----- Tenant Filter Registrarion -----");
		FilterRegistrationBean<TenantValidationFilter> registration = new FilterRegistrationBean<TenantValidationFilter>();
		registration.setFilter(filter);
		// Validate for all incoming request
		registration.addUrlPatterns("*");
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean<SubnetFilter> subnetIpFilterRegistration() {
		Log.platform.info("----- Subent IP Filter Registrarion -----");
		FilterRegistrationBean<SubnetFilter> registration = new FilterRegistrationBean<SubnetFilter>();
		registration.setFilter(subnetFilter);
		registration.addUrlPatterns("*");
		return registration;
	}

	@Bean
	public FilterRegistrationBean<UserTokenSecurityFilter> UserSecurityFilterRegistration() {
		Log.user.info("----- User Filter Registrarion -----");
		FilterRegistrationBean<UserTokenSecurityFilter> registration = new FilterRegistrationBean<UserTokenSecurityFilter>();
		registration.setFilter(userFilter);
		registration.addUrlPatterns(FilterUtil.getValidateUserTokenUrlPatterns()); 
		return registration;
	}
	
}
