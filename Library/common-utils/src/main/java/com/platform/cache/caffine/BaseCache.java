package com.platform.cache.caffine;

import com.platform.entity.BaseEntity;

/**
 * @author Muhil 
 */
public interface BaseCache {

	void put(String uniqueName, BaseEntity entity);

	void cleanUp();

	BaseEntity getIfPresent(String key);
	
}
