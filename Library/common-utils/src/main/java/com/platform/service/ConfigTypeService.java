package com.platform.service;

import java.util.List;

import com.platform.entity.ConfigType;

/**
 * @author Muhil 
 */
public interface ConfigTypeService extends BaseService {

	List<ConfigType> findConfigsByType(String configType);

}
