package com.platform.email;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.platform.entity.BaseEntity;
import com.platform.logging.Log;
import com.platform.service.LocalCacheService;

/**
 * @author Muhil
 */
@Service
public class EmailCacheService implements LocalCacheService {

	private Cache<String, Object> cache;

	public EmailCacheService() {
		this.cache = CacheBuilder.newBuilder().maximumSize(MAX_CACHED_OBJECTS)
				.expireAfterWrite(MAX_CACHE_TTL_MIN, TimeUnit.MINUTES)
				.expireAfterAccess(MAX_CACHE_TTL_MIN, TimeUnit.MINUTES).build();
	}

	@Override
	public Object get(String key) {
		Log.platform.debug(String.format("Fetching key %s from cache", key));
		return cache.getIfPresent(key);
	}

	@Override
	public void add(String key, Object value) {
		Log.platform.debug(String.format("Cached key %s ", key));
		cache.put(key, value);
	}

	@Override
	public void add(BaseEntity value) {
		Log.platform.debug(String.format("Cached key %s ", value.getRootid()));
		this.add(String.valueOf(value.getRootid()), value);
	}

	@Override
	public void evict(String key) {
		Log.platform.debug(String.format("Evicted key %s from cache", key));
		cache.invalidate(key);
	}

	@Override
	public void evictAll() {
		Log.platform.debug("Clearing cache");
		Log.platform.debug("Cahce size : {}", cache.size());
		cache.invalidateAll();
	}

}
