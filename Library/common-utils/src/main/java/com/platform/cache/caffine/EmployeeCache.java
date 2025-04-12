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
@ConditionalOnProperty(prefix = "platform.api.load", value = "users", havingValue = "true")
public class EmployeeCache implements BaseCache {

	private Cache<String, BaseEntity> cache;

	public EmployeeCache() {
		cache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(5)).maximumSize(100_00).build();
	}

	@Override
	public void put(String rootId, BaseEntity entity) {
		cache.put(rootId, entity);
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