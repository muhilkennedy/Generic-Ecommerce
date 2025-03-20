package com.user.model;

import java.util.List;

/**
 * @author Muhil
 */
public class EmployeeRoleRequest {

	private String name;
	private List<Long> permissionsIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getPermissionsIds() {
		return permissionsIds;
	}

	public void setPermissionsIds(List<Long> permissionsIds) {
		this.permissionsIds = permissionsIds;
	}

}
