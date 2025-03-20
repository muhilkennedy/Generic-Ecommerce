package com.tenant.serviceimpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.platform.entity.BaseEntity;
import com.platform.server.BaseSession;
import com.platform.service.EmailService;
import com.platform.service.FileStoreService;
import com.platform.util.ImageUtil;
import com.tenant.dao.TenantDaoService;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantSubscription;
import com.tenant.model.TenantRequest;
import com.tenant.model.TenantSubscriptionRequest;
import com.tenant.model.TenantWidgetResponse;
import com.tenant.service.TenantService;

/**
 * @author muhil 
 */
@Service
@Qualifier("TenantService")
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private TenantDaoService daoService;
	
	@Autowired
	private FileStoreService fileStore;

	@Override
	public BaseEntity findById(Long rootId) {
		return daoService.findById(rootId);
	}
	
    @Override
    public List<Tenant> findAll ()
    {
        return daoService.findAll();
    }

	@Override
	public Page<?> findAll(Pageable pageable) {
		return daoService.findAll(pageable);
	}
	
	public BaseEntity findByUniqueName(String uniqueName){
		return daoService.findByUniqueName(uniqueName);
	}
	
	@Autowired
	EmailService emailService;
	
	public Tenant createTenant(TenantRequest tenantRequest) {
		Tenant tenant = new Tenant();
		tenant.setName(tenantRequest.getName());
		tenant.setUniquename(tenantRequest.getUniqueName());
		tenant.setParent(tenantRequest.getParentId());
		tenant.setLocale(tenantRequest.getLocale() == null? Locale.ENGLISH.getLanguage() : tenantRequest.getLocale());
        tenant.setTimezone(
            tenantRequest.getTimeZone() == null ? TimeZone.getDefault().getID()
                : TimeZone.getTimeZone(tenantRequest.getTimeZone()).getID());
        try {
            tenant.setLogo(fileStore.getMediaUrl(tenantRequest.getLogoFileId()));
        }
        catch (FileNotFoundException e) {
           Assert.state(false, e.getMessage());
        }
		daoService.saveAndFlush(tenant);
		BaseSession.setTenant(tenant);//reset tenant info before persisting tenant related objects
		UpdateTenantDetails(tenantRequest);
		//send onboarding email
		return tenant;
	}

	public TenantDetails UpdateTenantDetails(TenantRequest tenantRequest) {
		TenantDetails details = new TenantDetails();
		details.setBusinessemail(tenantRequest.getBusinessEmail());
		details.setEmailid(tenantRequest.getEmailId());
		details.setCity(tenantRequest.getCity());
		details.setContact(tenantRequest.getMobile());
		details.setPincode(tenantRequest.getPincode());
		details.setStreet(tenantRequest.getStreet());
		details.setTagline(tenantRequest.getTagLine());
		details.setState(tenantRequest.getState());
        try {
            details.setLogothumbnail(
                fileStore.uploadToFileStore(
                    ImageUtil.getPNGThumbnailImage(
                        fileStore.getFileById(tenantRequest.getLogoFileId()),
                        false),
                    false).getMediaurl());
        }
        catch (IOException e) {
            Assert.state(false, e.getMessage());
        }
		Tenant tenant = (Tenant) BaseSession.getTenant();
		tenant.setTenantDetail(details);
		details.setTenant(tenant);
		daoService.saveAndFlush(tenant);
		return details;
	}
	
	public TenantSubscription updateTenantSubscription(TenantSubscriptionRequest subscriptionRequest) {
		TenantSubscription subscription = new TenantSubscription();
		subscription.setStartdate(subscriptionRequest.getStartDate());
		subscription.setEnddate(subscriptionRequest.getEndDate());
		return daoService.saveTenantSubscription(subscription);
	}

	public List<TenantSubscription> getTenantSubscriptionHistory() {
		return daoService.findAllSubcriptions();
	}
	
	public Tenant updateTenant (Long tenantId, TenantRequest tenantRequest) {
	    Tenant tenant = (Tenant)findById(tenantId);
	    if(StringUtils.isNotEmpty(tenantRequest.getLocale())) {
	        tenant.setLocale(tenantRequest.getLocale());
	    }
	    return (Tenant)daoService.save(tenant);
	}
	
	public TenantWidgetResponse getTenantsCountForDashBoard() {
	    TenantWidgetResponse resp = new TenantWidgetResponse();
	    resp.setTotalTenants(daoService.getAllTenantsCount());
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, -7);
	    resp.setRecentTenants(daoService.getAllTenantsFromTimeCount(calendar.getTime().getTime()));
	    return resp;
	}

}
