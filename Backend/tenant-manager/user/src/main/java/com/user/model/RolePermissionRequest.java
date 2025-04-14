package com.user.model;

import java.util.List;

/**
 * @author Muhil
 */
public class RolePermissionRequest {

	private Long roleId;
	private List<String> permissionNames;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<String> getPermissionNames() {
		return permissionNames;
	}

	public void setPermissionNames(List<String> permissionNames) {
		this.permissionNames = permissionNames;
	}

}
