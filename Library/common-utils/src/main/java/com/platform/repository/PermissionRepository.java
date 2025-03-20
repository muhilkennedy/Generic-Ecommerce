package com.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.entity.Permission;

/**
 * @author muhil
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
	
}