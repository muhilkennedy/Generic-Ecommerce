package com.platform.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.platform.entity.Permission;
import com.platform.repository.PermissionRepository;

/**
 * @author muhil
 */
@Service
public class PermissionsDaoService {

	private static final String PERMISSION_CACHE_NAME = "permission";

	@Autowired
	private PermissionRepository repository;

	@CachePut(value = PERMISSION_CACHE_NAME, key = "#obj.rootid")
	public Permission save(Permission obj) {
		return repository.save(obj);
	}

	@Cacheable(value = PERMISSION_CACHE_NAME, key = "#rootId")
	public Optional<Permission> findById(Long rootId) {
		return repository.findById(rootId);
	}

	public void delete(Permission obj) {
		throw new UnsupportedOperationException();
	}

	public List<Permission> findAll() {
		return repository.findAll();
	}

}
