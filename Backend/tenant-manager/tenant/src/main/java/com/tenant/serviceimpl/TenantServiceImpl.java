package com.tenant.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.platform.entity.BaseEntity;
import com.platform.entity.FileStore;
import com.platform.logging.Log;
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
@Primary
public class TenantServiceImpl implements TenantService {
	
	private static String LOGO_DIR = "LOGO";
	
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
	
	@Override
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
		//Update tenant info before persisting tenant related objects
		BaseSession.setTenant(tenant);
		BaseSession.SetTenantUniqueName(tenant.getUniqueName());
		UpdateTenantDetails(tenantRequest);
		// Move Logo to respective tenant folder, as previous logo would have been uploded into admin realm.
		try {
			tenant.setLogo(fileStore.moveFile(tenantRequest.getLogoFileId(), LOGO_DIR).getMediaurl());
		} catch (IOException e) {
			Log.tenant.error("Failed to move tenant logo from admin realm.", e);
		}
		//send onboarding email
		return (Tenant) daoService.saveAndFlush(tenant);
	}

	@Override
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
					fileStore
							.uploadToFileStore(ImageUtil.getPNGThumbnailImage(
									fileStore.getFileById(tenantRequest.getLogoFileId()), false), false, LOGO_DIR)
							.getMediaurl());
        }
        catch (IOException e) {
            Assert.state(false, e.getMessage());
        }
		Tenant tenant = (Tenant) BaseSession.getTenant();
		tenant.setTenantDetail(details);
		details.setTenant(tenant);
		return details;
	}
	
	@Override
	public TenantSubscription updateTenantSubscription(TenantSubscriptionRequest subscriptionRequest) {
		TenantSubscription subscription = new TenantSubscription();
		subscription.setStartdate(subscriptionRequest.getStartDate());
		subscription.setEnddate(subscriptionRequest.getEndDate());
		return daoService.saveTenantSubscription(subscription);
	}

	@Override
	public List<TenantSubscription> getTenantSubscriptionHistory() {
		return daoService.findAllSubcriptions();
	}
	
	@Override
	public Tenant updateTenant (Long tenantId, TenantRequest tenantRequest) {
	    Tenant tenant = (Tenant)findById(tenantId);
	    if(StringUtils.isNotEmpty(tenantRequest.getLocale())) {
	        tenant.setLocale(tenantRequest.getLocale());
	    }
	    return (Tenant)daoService.save(tenant);
	}
	
	@Override
	public TenantWidgetResponse getTenantsCountForDashBoard() {
	    TenantWidgetResponse resp = new TenantWidgetResponse();
	    resp.setTotalTenants(daoService.getAllTenantsCount());
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, -7);
	    resp.setRecentTenants(daoService.getAllTenantsFromTimeCount(calendar.getTime().getTime()));
	    return resp;
	}
	
	public void validateTenantSubscription() {
		Tenant tenant = (Tenant) BaseSession.getTenant();
		List<TenantSubscription> subscriptions = getTenantSubscriptionHistory();
		subscriptions.stream().filter(subscription -> subscription.isActive()).forEach(subscription -> {
			if(subscription.getEnddate().after(new Date())) {
				
			}
			if(subscription.getStartdate().after(new Date())) {
				subscription.setActive(true);
				tenant.setActive(true);
			}
			daoService.saveTenantSubscription(subscription);
			daoService.save(tenant);
		});
	}

}
