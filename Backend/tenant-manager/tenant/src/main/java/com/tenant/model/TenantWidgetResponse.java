package com.tenant.model;

/**
 * @author Muhil
 */
public class TenantWidgetResponse {
    
    private int totalTenants;
    private int recentTenants;

    public int getTotalTenants() {
	return totalTenants;
    }

    public void setTotalTenants(int totalTenants) {
	this.totalTenants = totalTenants;
    }

    public int getRecentTenants() {
	return recentTenants;
    }

    public void setRecentTenants(int recentTenants) {
	this.recentTenants = recentTenants;
    }

}
