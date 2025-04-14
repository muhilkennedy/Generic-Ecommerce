package com.config.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil Kennedy
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SubnetFilter extends OncePerRequestFilter {

	@Value("${app.trusted.subnets}")
	List<String> trustedSubnets;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		System.out.print("filter executed");
		if(isFromTrustedSubnet(httpRequest.getRemoteAddr())) {
			filterChain.doFilter(request, response);
		}
		else {
			throw new ServletException("Access Denied! Untrusted Subnet.");
		}
		filterChain.doFilter(request, response);
	}

	private boolean isFromTrustedSubnet(String ip) {
		return trustedSubnets.contains(ip);
	}

}
