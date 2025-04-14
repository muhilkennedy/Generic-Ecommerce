package com.tenant.dashboard;

import com.tenant.model.TenantWidgetResponse;
import com.user.model.EmployeeWidgetResponse;

/**
 * @author muhil
 */
public class DashBoardWidgets {

	private TenantWidgetResponse tenantWidget;
	private EmployeeWidgetResponse employeeWidget;

	public TenantWidgetResponse getTenantWidget() {
		return tenantWidget;
	}

	public void setTenantWidget(TenantWidgetResponse tenantWidget) {
		this.tenantWidget = tenantWidget;
	}

	public EmployeeWidgetResponse getEmployeeWidget() {
		return employeeWidget;
	}

	public void setEmployeeWidget(EmployeeWidgetResponse employeeWidget) {
		this.employeeWidget = employeeWidget;
	}

}
