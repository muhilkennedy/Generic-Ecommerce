package com.platform.model;

import java.util.List;

/**
 * @author Muhil 
 */
public class EmployeeResponse extends UserResponse {

	private static final long serialVersionUID = 1L;
	List<String> userPermissions;

	public List<String> getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(List<String> userPermissions) {
		this.userPermissions = userPermissions;
	}
	
}
