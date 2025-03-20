package com.user.api;

import java.io.IOException;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.server.BaseSession;
import com.platform.user.permissions.Permissions;
import com.user.entity.Employee;
import com.user.entity.Role;
import com.user.entity.User;
import com.user.model.EmployeeRoleRequest;
import com.user.service.EmployeeRolesService;
import com.user.service.UserService;

/**
 * @author muhil
 */
@RestController
@RequestMapping("employee")
@ValidateUserToken
public class EmployeeController {
	
	@Autowired
	@Qualifier("EmployeeService")
	private UserService empService;
	
	@Autowired
	private EmployeeRolesService empRoleService;
	
	@GetMapping
	public GenericResponse<User> getUser() throws SchedulerException, IOException {
		return new GenericResponse<User>().setData((User) BaseSession.getUser());
	}
	
	@PostMapping
	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
	public GenericResponse<User> createUser(@RequestBody Employee user) throws SchedulerException, IOException {
		return new GenericResponse<User>().setData(empService.register(user));
	}
	
//	@PostMapping(name = "/role")
//	@UserPermission(values = { Permissions.ADMIN })
//	public GenericResponse<Role> createEmployeeRoles(@RequestBody EmployeeRoleRequest request) throws SchedulerException, IOException {
//		return new GenericResponse<Role>().setData(empRoleService.createNewRole(request.getName(), request.getPermissionsIds()));
//	}
//	
//	@PostMapping(name = "/roles/{employeeId}")
//	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
//	public GenericResponse<User> assignEmployeeRole(@PathVariable Long employeeId, 
//			@RequestBody List<Long> roleId) throws SchedulerException, IOException {
//	    return new GenericResponse<User>().setData(empRoleService.addRoleToEmployee(employeeId, roleId));
//	}
//	
//	@GetMapping(name = "/roles")
//	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
//	public GenericResponse<Role> getAllRoles() throws SchedulerException, IOException {
//	    return new GenericResponse<Role>().setDataList(empRoleService.getAllAvailableRoles());
//	}
//	
//	@GetMapping(name = "/roles/{employeeId}")
//	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
//	public GenericResponse<Role> getEmployeeRoles(@PathVariable Long employeeId) throws SchedulerException, IOException {
//	    return new GenericResponse<Role>().setDataList(empRoleService.getEmployeeRoles(employeeId));
//	}

}
