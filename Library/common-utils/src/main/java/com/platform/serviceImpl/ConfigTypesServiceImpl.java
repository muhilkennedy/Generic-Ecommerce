package com.platform.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.configurations.ConfigTypes;
import com.platform.dao.ConfigTypeDaoService;
import com.platform.email.EmailCacheService;
import com.platform.entity.BaseEntity;
import com.platform.entity.ConfigType;
import com.platform.exceptions.ConfigTypeException;
import com.platform.model.ConfigRequest;
import com.platform.server.BaseSession;
import com.platform.service.ConfigTypeService;

import io.jsonwebtoken.lang.Arrays;

/**
 * @author Muhil
 */
@Service
public class ConfigTypesServiceImpl implements ConfigTypeService {

	@Autowired
	private ConfigTypeDaoService daoService;

	@Autowired
	private EmailCacheService cacheService;

	@Override
	public BaseEntity findById(Long rootId) {
		return daoService.findById(rootId);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		return daoService.findAll(pageable);
	}

	public List<ConfigType> createConfigType(ConfigRequest request) throws ConfigTypeException {
		String type = request.getType();
		if (Arrays.asList(ConfigTypes.values()).stream().filter(config -> config.name().equals(type)).findAny()
				.isEmpty()) {
			throw new ConfigTypeException("Invalid config type");
		}
		// clear incase of any update to the table
		List<ConfigType> configs = findConfigsByType(type);
		cacheService.evictAll();
		// delete all existing configs and recreate new configs, assuming this is not a frequent op.
		if (configs != null && !configs.isEmpty()) {
			configs.stream().forEach(config -> daoService.delete(config));
		}
		request.getConfigType().entrySet().forEach(entry -> {
			ConfigType ctype = new ConfigType();
			ctype.setType(type);
			ctype.setName(entry.getKey());
			ctype.setValue(entry.getValue());
			daoService.save(ctype);
		});
		return findConfigsByType(type);
	}

	@Override
	public List<ConfigType> findConfigsByType(String configType) {
		if (cacheService.get(getCacheKey(configType)) == null) {
			List<ConfigType> configs = daoService.findAllConfigsbyType(configType);
			cacheService.add(getCacheKey(configType), configs);
		}
		return (List) cacheService.get(getCacheKey(configType));
	}

	private String getCacheKey(String configType) {
		return BaseSession.getTenantUniqueName().concat(configType);
	}

}
