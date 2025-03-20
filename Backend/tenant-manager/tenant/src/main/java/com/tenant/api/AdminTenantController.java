package com.tenant.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.user.permissions.Permissions;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantSubscription;
import com.tenant.model.TenantRequest;
import com.tenant.model.TenantSubscriptionRequest;
import com.tenant.model.TenantWidgetResponse;
import com.tenant.serviceimpl.TenantServiceImpl;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("admin/tenant")
@ValidateUserToken
public class AdminTenantController
{

    @Autowired
    private TenantServiceImpl tenantService;

    @PostMapping
    @UserPermission(values = { Permissions.SUPER_USER })
    public GenericResponse<Tenant> createTenant (@RequestBody TenantRequest tenant)
    {
        return new GenericResponse<Tenant>(tenantService.createTenant(tenant));
    }
    
    @GetMapping("/all")
    @UserPermission(values = { Permissions.SUPER_USER })
    public GenericResponse<Tenant> getAllTenants (@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "pageLimit", required = false) Integer pageLimit)
    {
        //TODO: impl pagination.
        return new GenericResponse<Tenant>(tenantService.findAll());
    }
    
    @GetMapping("/all/count")
    @UserPermission(values = { Permissions.SUPER_USER })
    public GenericResponse<TenantWidgetResponse> getAllTenantsCount ()
    {
        return new GenericResponse<TenantWidgetResponse>(tenantService.getTenantsCountForDashBoard());
    }

    @PostMapping(value = "/subscription")
    @UserPermission(values = { Permissions.SUPER_USER })
    public GenericResponse<TenantSubscription> createTenantSubscription (@RequestParam Long tenantId,
                                                                         @RequestBody TenantSubscriptionRequest tenantSubscription)
    {
        return new GenericResponse<TenantSubscription>(
            tenantService.updateTenantSubscription(tenantSubscription)).setDataList(
                tenantService.getTenantSubscriptionHistory());
    }
    
    @GetMapping(value = "/subscription")
    @UserPermission(values = { Permissions.SUPER_USER })
    public GenericResponse<TenantSubscription> getTenantSubscription (@RequestParam Long tenantId)
    {
        return new GenericResponse<TenantSubscription>().setDataList(
                tenantService.getTenantSubscriptionHistory());
    }
    
    @PutMapping
    @UserPermission(values = { Permissions.SUPER_USER })
    public GenericResponse<Tenant> updateTenant (@RequestParam Long tenantId,
                                                 @RequestBody TenantRequest tenant)
    {
        return new GenericResponse<Tenant>(tenantService.updateTenant(tenantId, tenant));
    }

}
