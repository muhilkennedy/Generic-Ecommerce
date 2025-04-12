package com.platform.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.cache.caffine.EmployeeCache;
import com.platform.entity.BaseEntity;
import com.platform.exceptions.DataException;
import com.platform.messages.GenericResponse;
import com.platform.model.EmployeeResponse;
import com.platform.service.BaseService;
import com.platform.util.HttpUtilReactive;
import com.platform.util.PropertiesUtil;

import reactor.core.publisher.Mono;

/**
 * @author Muhil Should be extended by MicroServices that pull tenant data.
 */
@Service
@Qualifier("EmployeeService")
public class EmployeeServiceMSImpl implements BaseService {

	@Autowired(required = false)
	private EmployeeCache empCache;

	@Override
	public BaseEntity findById(Long rootId) {
		if (empCache.getIfPresent(rootId.toString()) == null) {
			Mono<?> tenantMono = HttpUtilReactive.getMonoGenericResponse(
					PropertiesUtil.getTenantMSBaseUrl() + String.format("/employee/%s", rootId), EmployeeResponse.class);
			GenericResponse<EmployeeResponse> response = (GenericResponse<EmployeeResponse>) tenantMono.block();
			if (response == null) {
				throw new DataException("Fetch employee api response is empty");
			} else {
				empCache.put(rootId.toString(), response.getData());
			}
		}
		return empCache.getIfPresent(rootId.toString());
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		throw new UnsupportedOperationException();
	}

}
