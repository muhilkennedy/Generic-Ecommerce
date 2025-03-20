package com.tenant.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.service.BaseDaoService;
import com.platform.util.PlatformUtil;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantSubscription;
import com.tenant.repository.TenantRepository;
import com.tenant.repository.TenantSubscriptionRepository;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("TenantDao")
public class TenantDaoService implements BaseDaoService {
	
	private static final String TENANT_CACHE_NAME = "tenant";

	@Autowired
	private TenantRepository tenantRepository;
	
	@Autowired
	private TenantSubscriptionRepository subscriptionRepository;

	@Autowired
	private CacheManager cacheManager;

	@Override
	@CachePut(value = TENANT_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity save(BaseEntity obj) {
		Tenant tenant = (Tenant) obj;
		//evictTenantByUniqueName(tenant.getUniquename());
		return tenantRepository.save(tenant);
	}

	@Override
	@CachePut(value = TENANT_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		Tenant tenant = (Tenant) obj;
		//evictTenantByUniqueName(tenant.getUniquename());
		return tenantRepository.saveAndFlush(tenant);
	}

	@Override
	@Cacheable(value = TENANT_CACHE_NAME, key = "#rootId", unless="#result == null")
	public BaseEntity findById(Long rootId) {
		return tenantRepository.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = TENANT_CACHE_NAME, key = "#obj.rootid")
	public void delete(BaseEntity obj) {
		tenantRepository.delete((Tenant) obj);
	}

	@Override
	public List<Tenant> findAll() {
		return tenantRepository.findAll();
	}

	@Override
	@CacheEvict(value = TENANT_CACHE_NAME, key = "#rootId")
	public void deleteById(Long rootId) {
		tenantRepository.deleteById(rootId);
	}

	@Cacheable(value = TENANT_CACHE_NAME, key = "#uniqueName", unless="#result == null")
	public Tenant findByUniqueName(String uniqueName) {
		return tenantRepository.findTenantByUniqueName(uniqueName);
	}
	
	@Override
	public Page<?> findAll(Pageable pageable) {
		return tenantRepository.findAll(pageable);
	}

	private void evictTenantByUniqueName(String uniqueName) {
		cacheManager.getCache(TENANT_CACHE_NAME).evictIfPresent(uniqueName);
	}
	
	public TenantSubscription saveTenantSubscription(TenantSubscription sub) {
		return subscriptionRepository.save(sub);
	}

	public List<Long> findTenantsToActivate() {
		Date date = new Date();
		String dt = PlatformUtil.SIMPLE_DATE_ONLY_FORMAT.format(date.getTime());
		return subscriptionRepository.findTS(dt);
	}

	public List<Long> findTenantsToDeactivate() {
		Date date = new Date();
		String dt = PlatformUtil.SIMPLE_DATE_ONLY_FORMAT.format(date.getTime());
		return subscriptionRepository.findExpireTS(dt);
	}

	public List<TenantSubscription> findAllSubcriptions() {
		return subscriptionRepository.findAll();
	}
	
	public Integer getAllTenantsCount() {
	    return tenantRepository.findTenantsCount();
	}
	
	public Integer getAllTenantsFromTimeCount(Long time) {
	    return tenantRepository.findTenantsCountFromTime(time);
	}

}
