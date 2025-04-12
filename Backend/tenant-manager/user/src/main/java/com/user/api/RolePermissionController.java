package com.user.api;

import java.io.IOException;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.user.permissions.Permissions;
import com.user.entity.Role;
import com.user.entity.User;
import com.user.model.EmployeeRoleRequest;
import com.user.service.EmployeeRolesService;

/**
 * @author muhil
 */
@RestController
@RequestMapping("role")
@ValidateUserToken
public class RolePermissionController {
	
	@Autowired
	private EmployeeRolesService empRoleService;
	
	@GetMapping
	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
	public GenericResponse<Role> getAllRoles() throws SchedulerException, IOException {
	    return new GenericResponse<Role>().setDataList(empRoleService.getAllAvailableRoles());
	}
	
	@PostMapping
	@UserPermission(values = { Permissions.ADMIN })
	public GenericResponse<Role> createEmployeeRoles(@RequestBody EmployeeRoleRequest request) throws SchedulerException, IOException {
		return new GenericResponse<Role>().setData(empRoleService.createNewRole(request.getName(), request.getPermissionsIds()));
	}
	
	@PostMapping(value = "/assign/{employeeId}")
	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
	public GenericResponse<User> assignEmployeeRole(@PathVariable Long employeeId, 
			@RequestBody List<Long> roleId) throws SchedulerException, IOException {
	    return new GenericResponse<User>().setData(empRoleService.addRoleToEmployee(employeeId, roleId));
	}
	
	@GetMapping(value = "/{employeeId}")
	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
	public GenericResponse<Role> getEmployeeRoles(@PathVariable Long employeeId) throws SchedulerException, IOException {
	    return new GenericResponse<Role>().setDataList(empRoleService.getEmployeeRoles(employeeId));
	}
	
	@GetMapping(value = "/permissions")
	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
	public GenericResponse<Permissions> getAllPermissions() throws SchedulerException, IOException {
	    return new GenericResponse<Permissions>().setDataList(Permissions.getAllPermissions());
	}

}
