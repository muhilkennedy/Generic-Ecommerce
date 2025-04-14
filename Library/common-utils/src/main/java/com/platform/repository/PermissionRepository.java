package com.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.platform.entity.Permission;

/**
 * @author muhil
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
	
	String findByPermissionNameQuery = "select perm from Permission perm where perm.permission=:name";

	@Query(findByPermissionNameQuery)
	Permission findByPermissionName(@Param("name") String name);
	
}