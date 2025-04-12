package com.platform.serviceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.platform.entity.AuditLog;
import com.platform.entity.BaseEntity;
import com.platform.logging.AuditOperation;
import com.platform.repository.AuditRepository;
import com.platform.server.BaseSession;
import com.platform.service.AuditService;

/**
 * @author Muhil
 */
@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditRepository auditRepo;

	@Override
	public BaseEntity findById(Long rootId) {
		return auditRepo.findById(rootId).get();
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		return auditRepo.findAll(pageable);
	}

	private void audit(String message, AuditOperation operation, String affectedEntity, Long affectedRootId,
			String auditId) {
		AuditLog log = new AuditLog();
		log.setMessage(message);
		log.setOperation(operation.name());
		log.setAffectedentity(affectedEntity);
		log.setAffectedrootid(affectedRootId);
		if (StringUtils.isNotBlank(auditId)) {
			log.setAuditid(auditId);
		}
		auditRepo.save(log);
	}

	/**
	 * @param tenant
	 * @param user
	 * @param message
	 * @param operation
	 * @param affectedEntity 
	 * Async method to perform audit logging.
	 */
	@Override
	@Async
	public void logAudit(BaseEntity tenant, BaseEntity user, String message, AuditOperation operation,
			Object affectedEntity) {
		logAudit(tenant, user, message, operation, affectedEntity, null);
	}

	@Override
	@Async
	public void logAudit(BaseEntity tenant, BaseEntity user, String message, AuditOperation operation,
			Object affectedEntity, String auditId) {
		try {
			BaseSession.setupSession(tenant, user);
			if (affectedEntity instanceof BaseEntity entity) {
				audit(message, operation, entity.getClass().getCanonicalName(), entity.getRootid(), auditId);
			} else {
				audit(message, operation, affectedEntity.getClass().getCanonicalName(), null, auditId);
			}
		} finally {
			BaseSession.tearDownSession();
		}
	}

}
