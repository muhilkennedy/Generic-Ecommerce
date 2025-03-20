package com.platform.security.filters;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.platform.entity.BaseEntity;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.platform.service.BaseService;
import com.platform.util.PlatformUtil;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil Kennedy 
 * Tenant filter to make sure only valid clients proceed further.
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantValidationFilter extends OncePerRequestFilter {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("TenantService")
	private BaseService tenantService;

	@Value("${app.whitelist.urls}")
	private List<String> Whitelisted_URI;

	@Value("${app.trusted.subnets}")
	private List<String> trustedSubnets;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return Whitelisted_URI.parallelStream()
				.filter(uri -> request.getRequestURI() != null && request.getRequestURI().contains(uri))
				.peek(uri -> Log.tenant.debug("Filtered URI -> {}", uri)).findAny().isPresent();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		Log.tenant.info("Request URI - {} : remote address - {} : user agent : {}", requestUri, request.getRemoteAddr(),
				request.getHeader(HttpHeaders.USER_AGENT));
		// error out if no teant header is present
		String tenantUniqueName = request.getHeader(PlatformUtil.TENANT_HEADER);
		if (StringUtils.isBlank(tenantUniqueName)) {
			Log.tenant.error("Tenant Header is Empty");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tenant Header is Empty");
			return;
		}
		BaseEntity tenant = tenantService.findByUniqueName(tenantUniqueName);
		if (!isValidTenant(tenant, response)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, messageSource
					.getMessage("tenant.inactive", new String[] { tenantUniqueName }, Locale.ENGLISH));
			return;
		}
		/*
		 * setting current tenant here indicates, he is the owner of this session. (but
		 * based on tenant id on parameter, tenant object will be updated so object
		 * persistance happens for tenant passed as parameter. In short tenantId passed
		 * as parameter will take precedence.(header acts as on behalf of, i.e usually
		 * for superusers)
		 */
		BaseSession.setCurrentTenant(tenant);
		Log.tenant.debug("Tenant Session For {} is setup for request {} from origin {}", tenant.getUniqueName(),
				requestUri, request.getHeader(HttpHeaders.ORIGIN));
		/*
		 * TenantDetails td = tenant.getTenantDetail();
		 * if(PropertiesUtil.isProdDeployment() && td.getDetails().isInitialSetUpDone())
		 * { String origin = request.getHeader(HttpHeaders.ORIGIN); if
		 * (!(origin.equals(td.getDetails().getAdminUrl()) ||
		 * origin.equals(td.getDetails().getClientUrl()))) {
		 * Log.tenant.error("Invalid tenant origin");
		 * response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,
		 * "Invalid tenant origin"); return; } }
		 */
		// Set current session as new tenant (tenantId passed in parameter takes
		// precedence here)
		String idParam = request.getParameter(PlatformUtil.TENANT_PARAM);
		if (!StringUtils.isAllBlank(idParam)) {
			Long id = Long.parseLong(idParam);
			tenant = tenantService.findById(id);
			if (tenant != null) {
				BaseSession.setTenant(tenant);
				Log.platform.info("Tenant Session Updated for {} to {} based on request",
						BaseSession.getCurrentTenant().getUniqueName(), tenant.getUniqueName());
			} else {
				throw new RuntimeException("Invalid Request for Tenant");
			}
		}
		Log.tenant.debug("Tenant filter validation successful");
		filterChain.doFilter(request, response);
	}

	@PostConstruct
	private void whiteListedEndpoints() {
		Log.tenant.warn("Whitelisted Uris : ");
		Whitelisted_URI.parallelStream().forEach(uri -> Log.tenant.warn(uri));
	}

	private boolean isValidTenant(BaseEntity tenant, HttpServletResponse response) throws IOException {
		if (tenant == null) {
			Log.tenant.error("Tenant Not Found");
			return false;
		}
		if (!tenant.isActive()) {
			Log.tenant.error("Tenant Not Active");
			response.sendError(HttpServletResponse.SC_FORBIDDEN, messageSource
					.getMessage("tenant.inactive", new String[] { tenant.getUniqueName() }, Locale.ENGLISH));
			return false;
		}
		return true;
	}

	private boolean isFromTrustedSubnet(String ip, HttpServletResponse response) throws IOException {
		if (StringUtils.isAllBlank(ip)) {
			Log.tenant.error("Invalid Request from Untrusted subnet addr.");
			return false;
		}
		if (trustedSubnets.stream().filter(subnet -> ip.startsWith(subnet)).findAny().isPresent()) {
			return true;
		}
		return false;
	}

}
