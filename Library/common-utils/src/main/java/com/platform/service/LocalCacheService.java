package com.platform.service;

import com.platform.entity.BaseEntity;

/**
 * @author Muhil
 *
 */
public interface LocalCacheService {

	final int MAX_CACHED_OBJECTS = 500;
	final int MAX_CACHE_TTL_MIN = 10;

	Object get(String key);

	void add(String key, Object value);
	
	void add(BaseEntity value);
	
	void evict(String key);
	
	void evictAll();
	
}
