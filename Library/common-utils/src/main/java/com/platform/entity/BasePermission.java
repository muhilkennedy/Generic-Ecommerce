package com.platform.entity;

import java.util.Set;

import com.platform.user.permissions.Permissions;

/**
 * @author Muhil
 *
 */
public interface BasePermission {

	Set<Permissions> getUserPermissions();
	
}
