package com.tenant.service;

import java.util.List;

import com.platform.service.BaseService;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantSubscription;
import com.tenant.model.TenantRequest;
import com.tenant.model.TenantSubscriptionRequest;
import com.tenant.model.TenantWidgetResponse;

/**
 * @author muhil
 */
public interface TenantService extends BaseService {

	Tenant createTenant(TenantRequest tenantRequest);

	TenantDetails UpdateTenantDetails(TenantRequest tenantRequest);

	TenantSubscription updateTenantSubscription(TenantSubscriptionRequest subscriptionRequest);

	List<TenantSubscription> getTenantSubscriptionHistory();

	Tenant updateTenant(Long tenantId, TenantRequest tenantRequest);

	TenantWidgetResponse getTenantsCountForDashBoard();

}
