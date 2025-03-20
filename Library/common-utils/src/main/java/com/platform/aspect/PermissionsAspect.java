package com.platform.aspect;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.platform.annotations.UserPermission;
import com.platform.entity.BasePermission;
import com.platform.exceptions.InvalidUserPermission;
import com.platform.logging.Log;
import com.platform.server.BaseSession;
import com.platform.user.permissions.Permissions;

/**
 * @author Muhil
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class PermissionsAspect {
	
	@Autowired
	private MessageSource messageSource;

	@Pointcut("@annotation(com.platform.annotations.UserPermission)")
	protected void userTokenPointCut() {

	}

	@Around(value = "userTokenPointCut ()")
	public Object validateUserPermissions(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		UserPermission annotation = method.getAnnotation(UserPermission.class);
		Permissions[] permissions = annotation.values();
		// validate for permissions only if present in annotation.
		if (permissions.length > 0) {
			BasePermission user = (BasePermission) BaseSession.getUser(); // since employee entity implements basepermission
			if (user.getUserPermissions().isEmpty()) {
				Log.user.debug(
						"Seems like user dont have any permissions setup! consider adding roles/permissions before accessing endpoints {}");
				Log.user.debug("Required permission(s) to access this endpoint {}", permissions);
				throw new InvalidUserPermission(messageSource.getMessage("user.no.permission", null, BaseSession.getLocale()));
			}
			// Super_User can access all endpoints
			if (!user.getUserPermissions().contains(Permissions.SUPER_USER)) {
				if (!Stream.of(permissions).filter(permission -> user.getUserPermissions().contains(permission))
						.findAny().isPresent()) {
					Log.user.error("Authorization denied for user to access this endpoint");
					Log.user.debug("Required permission(s) to access this endpoint {}", permissions);
					throw new InvalidUserPermission(messageSource.getMessage("user.permission.denied", null, BaseSession.getLocale()));
				}
			} else {
				Log.user.debug(
						String.format("Looks like user has Super_User permission, proceeding with the request!"));
			}
		}
		Log.user.debug(String.format("User Permissions are valid for method : %s required permissions %s",
				method.getName(), Permissions.getPermissionsAsString(permissions)));
		Object resultObj = joinPoint.proceed();
		return resultObj;

	}

}
