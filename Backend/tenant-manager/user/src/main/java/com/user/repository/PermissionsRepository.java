package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platform.entity.Permission;

/**
 * @author Mughil
 */
@Repository
public interface PermissionsRepository extends JpaRepository<Permission, Long>{

}
