package com.platform.service;

import java.util.List;

import com.platform.entity.Permission;

/**
 * @author muhil 
 */
public interface PermissionsService {

	Permission findById(Long rootId);
	
	List<Permission> getAllPermissions();

}
