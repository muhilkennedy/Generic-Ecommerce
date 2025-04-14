package com.tenant.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.server.BaseSession;
import com.platform.user.permissions.Permissions;
import com.tenant.service.TenantService;
import com.user.entity.Employee;
import com.user.service.EmployeeService;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("dashboard")
@ValidateUserToken
public class DashboardController {

	@Autowired
	private TenantService tenantService;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/widgets")
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN, Permissions.MANAGE_USERS })
	public GenericResponse<DashBoardWidgets> getAllTenantsCount() {
		DashBoardWidgets widget = new DashBoardWidgets();
		Employee emp = (Employee) BaseSession.getUser();
		if (emp.getUserPermissions().contains(Permissions.SUPER_USER)) {
			widget.setTenantWidget(tenantService.getTenantsCountForDashBoard());
		}
		if (emp.getUserPermissions().contains(Permissions.SUPER_USER)
				|| emp.getUserPermissions().contains(Permissions.ADMIN)
				|| emp.getUserPermissions().contains(Permissions.MANAGE_USERS)) {
			widget.setEmployeeWidget(employeeService.getUsersCountForDashBoard());
		}
		return new GenericResponse<DashBoardWidgets>(widget);
	}

}
