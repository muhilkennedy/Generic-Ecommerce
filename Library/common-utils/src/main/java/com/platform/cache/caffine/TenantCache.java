package com.platform.cache.caffine;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.platform.entity.BaseEntity;

/**
 * @author Muhil
 */
@Component
@ConditionalOnProperty(prefix = "platform.api.load", value = "tenants", havingValue = "true")
public class TenantCache implements BaseCache {

	private Cache<String, BaseEntity> cache;

	public TenantCache() {
		cache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(5)).maximumSize(1000).build();
	}

	@Override
	public void put(String uniqueName, BaseEntity entity) {
		cache.put(uniqueName, entity);
	}

	@Override
	public void cleanUp() {
		cache.cleanUp();
	}

	@Override
	public BaseEntity getIfPresent(String key) {
		return cache.getIfPresent(key);
	}

}
