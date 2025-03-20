package com.user.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.platform.server.BaseSession;
import com.platform.util.JWTUtil;
import com.platform.util.LocaleUtil;
import com.user.entity.User;
import com.user.service.UserService;

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
@Order(2)
public class UserTokenSecurityFilter implements Filter {

	@Autowired
	@Qualifier("EmployeeService")
	private UserService employeeService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isNotBlank(token)) {
			String jwtToken = JWTUtil.extractToken(token);
			if (StringUtils.isNotBlank(jwtToken)) {
				try {
					if (JWTUtil.validateToken(jwtToken)) {
						String userRootId = JWTUtil.getUserIdFromToken(jwtToken);
						if (JWTUtil.isEmployeeUser(jwtToken)) {
							User user = (User) employeeService.findById(Long.valueOf(userRootId));
							String tokenUserUniqueName = JWTUtil.getUserUniqueNameFromToken(jwtToken);
							String tokenIpAddress = JWTUtil.getIpAddressFromToken(jwtToken);
							if (user == null || !user.getUniquename().equals(tokenUserUniqueName)
									|| !httpRequest.getRemoteAddr().equals(tokenIpAddress)) {
								httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, LocaleUtil
										.getLocalisedString("user.invalidAccess"));
								return;
							} else if (!user.isActive()) { 
								httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
										LocaleUtil.getLocalisedString("user.inactive"));
								return;
							}
							BaseSession.setUser(user);
							BaseSession.setLocale(user.getLocale());
							chain.doFilter(request, response);
						} else {
							// implement customer user logic here
							httpResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Impl pending - customer filter");
							return;
						}
					} else {
						httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								LocaleUtil.getLocalisedString("user.validationFailed"));
						return;
					}
				} catch (ExpiredJwtException ex) {
					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							LocaleUtil.getLocalisedString("user.tokenExpired"));
					return;
				}
			} else {
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
