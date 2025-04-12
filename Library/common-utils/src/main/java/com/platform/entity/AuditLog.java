package com.platform.entity;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Muhil
 */
@Entity
@Table(name = "AUDITLOG")
@ClassMetaProperty(code = "AL")
public class AuditLog extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	private static final int ERROR_SIZE_LIMIT = 2048;

	@Column(name = "AUDITID")
	private String auditid;

	@Column(name = "MESSAGE", length = 2048)
	private String message;

	@Column(name = "OPERATION")
	private String operation;

	@Column(name = "AFFECTEDENTITY")
	private String affectedentity;

	@Column(name = "AFFECTEDROOTID")
	private Long affectedrootid;

	public String getAuditid() {
		return auditid;
	}

	public void setAuditid(String auditid) {
		this.auditid = auditid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getAffectedentity() {
		return affectedentity;
	}

	public void setAffectedentity(String affectedentity) {
		this.affectedentity = affectedentity;
	}

	public Long getAffectedrootid() {
		return affectedrootid;
	}

	public void setAffectedrootid(Long affectedrootid) {
		this.affectedrootid = affectedrootid;
	}
	
	@PrePersist
	private void prePersist() {
		if (StringUtils.isNotBlank(message) && message.length() >= ERROR_SIZE_LIMIT) {
			this.message = message.substring(0, ERROR_SIZE_LIMIT);
		} else {
			this.message = message;
		}
		if (StringUtils.isAllBlank(auditid)) {
			auditid = UUID.randomUUID().toString();
		}
	}

}
