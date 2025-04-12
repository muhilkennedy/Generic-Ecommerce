package com.platform.security.filters;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.platform.entity.BaseEntity;
import com.platform.entity.BaseLocale;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.platform.service.BaseService;
import com.platform.util.JWTUtil;
import com.platform.util.LocaleUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 *
 */
@Component
@Order(3)
public class UserTokenSecurityFilter implements Filter {

	@Autowired
	@Qualifier("EmployeeService")
	private BaseService employeeService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isNotBlank(token)) {
			String jwtToken = JWTUtil.extractToken(token);
			if (StringUtils.isNotBlank(jwtToken)) {
				BaseSession.setJwttoken(jwtToken);
				try {
					if (JWTUtil.validateToken(jwtToken)) {
						String userRootId = JWTUtil.getUserIdFromToken(jwtToken);
						if (JWTUtil.isEmployeeUser(jwtToken)) {
							BaseEntity user = employeeService.findById(Long.valueOf(userRootId));
							String tokenUserUniqueName = JWTUtil.getUserUniqueNameFromToken(jwtToken);
							String tokenIpAddress = JWTUtil.getIpAddressFromToken(jwtToken);
							if (user == null || !user.getUniqueName().equals(tokenUserUniqueName)) {
								Log.user.error("Invalid user in token : {} : user : {}", BaseSession.getTenantUniqueName(), user.getUniqueName());
								httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, LocaleUtil
										.getLocalisedString("user.invalidAccess"));
								return;
							}
							else if((!httpRequest.getRemoteAddr().equals(tokenIpAddress) && !BaseSession.isRquestFromTrustedSubnet())) {
								Log.user.error("Invalid ip in token : {} : ip : {}", BaseSession.getTenantUniqueName(), httpRequest.getRemoteAddr());
								httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, LocaleUtil
										.getLocalisedString("user.invalidIP"));
								return;
							}
							else if (!user.isActive()) {
								Log.user.error("Inactive user : {} : {}", BaseSession.getTenantUniqueName(), user.getUniqueName());
								httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
										LocaleUtil.getLocalisedString("user.inactive"));
								return;
							}
							BaseSession.setUser(user);
							if (user instanceof BaseLocale userLocale) {
								BaseSession.setLocale(userLocale.getLocale());
							}
							chain.doFilter(request, response);
						} else {
							// implement customer user logic here
							httpResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Impl pending - customer filter");
							return;
						}
					} else {
						Log.user.error("Invalid user token : {} ", BaseSession.getTenantUniqueName());
						httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								LocaleUtil.getLocalisedString("user.validationFailed"));
						return;
					}
				} catch (ExpiredJwtException ex) {
					Log.user.error("Exception parsing user token : {} : {}", BaseSession.getTenantUniqueName(), ex);
					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							LocaleUtil.getLocalisedString("user.tokenExpired"));
					return;
				}
			} else {
				Log.user.error("User token missing : {}", BaseSession.getTenantUniqueName());
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
						LocaleUtil.getLocalisedString("user.tokenMissing"));
				return;
			}
		} else {
			// load some default user
			httpResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Impl pending - user filter");
			return;
		}
	}

}
