package com.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.entity.AuditLog;

/**
 * @author Muhil 
 */
public interface AuditRepository extends JpaRepository<AuditLog, Long> {

}
