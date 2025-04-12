package com.platform.service;

import com.platform.entity.BaseEntity;
import com.platform.logging.AuditOperation;

/**
 *	@author muhil 
 */
public interface AuditService extends BaseService {

	/**
	 * @param tenant
	 * @param user
	 * @param message
	 * @param operation
	 * @param affectedEntity
	 * Async method to perform audit logging.
	 */
	void logAudit(BaseEntity tenant, BaseEntity user, String message, AuditOperation operation, Object affectedEntity);
	
	void logAudit(BaseEntity tenant, BaseEntity user, String message, AuditOperation operation, Object affectedEntity, String auditId);

}
