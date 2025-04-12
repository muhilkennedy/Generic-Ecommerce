package com.platform.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.cache.caffine.TenantCache;
import com.platform.entity.BaseEntity;
import com.platform.exceptions.DataException;
import com.platform.messages.GenericResponse;
import com.platform.model.TenantResponse;
import com.platform.service.BaseService;
import com.platform.util.HttpUtilReactive;
import com.platform.util.PropertiesUtil;

import reactor.core.publisher.Mono;

/**
 * @author Muhil 
 * Should be extended by MicroServices that pull tenant data.
 */
@Service
@Qualifier("TenantService")
public class TenantServiceMSImpl implements BaseService {
	
	@Autowired(required = false)
	private TenantCache tenantCache;

	@Override
	public BaseEntity findById(Long rootId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		throw new UnsupportedOperationException();
	}
	
	public List<?> findAll() {
		Mono<?> tenantMono = HttpUtilReactive.getMonoGenericResponse(PropertiesUtil.getTenantMSBaseUrl() + "/admin/tenant/all", TenantResponse.class);
		GenericResponse<BaseEntity> response = (GenericResponse<BaseEntity>) tenantMono.block();
		if (response == null || response.getDataList() == null) {
			throw new DataException("Fetch tenant api response is empty");
		}
		return response.getDataList();
	}

	@Override
	public BaseEntity findByUniqueName(String uniqueName) {
		if (tenantCache.getIfPresent(uniqueName) == null) {
			Mono<?> tenantMono = HttpUtilReactive.getMonoGenericResponse(PropertiesUtil.getTenantMSBaseUrl() + "/ping", TenantResponse.class);
			GenericResponse<BaseEntity> response = (GenericResponse<BaseEntity>) tenantMono.block();
			if (response == null) {
				throw new DataException("Fetch tenant api response is empty");
			} else {
				tenantCache.put(uniqueName, response.getData());
			}
		}
		return tenantCache.getIfPresent(uniqueName);
	}

}
