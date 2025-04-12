package com.platform.security.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.platform.server.BaseSession;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil Kennedy
 *
 */
@Component
@Order(2)
public class SubnetFilter extends OncePerRequestFilter {

	@Value("${app.trusted.subnets}")
	List<String> trustedSubnets;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		BaseSession.setRequestFromTrustedSubnet(isFromTrustedSubnet(httpRequest.getRemoteAddr()));
		filterChain.doFilter(request, response);
	}

	private boolean isFromTrustedSubnet(String ip) {
		return trustedSubnets.contains(ip);
	}

}
