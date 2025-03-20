package com.platform.entity;

import org.apache.commons.lang3.StringUtils;

import com.platform.annotations.ClassMetaProperty;
import com.platform.util.PlatformUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "QRTZ_JOBHISTORY")
@ClassMetaProperty(code = "QTZJOBHIS")
public class QuartzJobHistory extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	private static final int ERROR_SIZE_LIMIT = 2048;
	
	@Column(name = "UUID", unique = true)
	private String uuid;

	@Column(name = "ERRORINFO", length = 2048)
	private String errorinfo;

	@Column(name = "TENANTID")
	private Long tenantid = PlatformUtil.SYSTEM_REALM_ROOTID;

	@Column(name = "JOBSTATUS")
	private String jobstatus;
	
	@Column(name = "JOBINFOID")
	private Long jobinfoid;

	public QuartzJobHistory() {
		super();
	}

	public Long getTenantId() {
		return tenantid;
	}

	public void setTenantId(Long tenantId) {
		this.tenantid = tenantId;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public Long getTenantid() {
		return tenantid;
	}

	public void setTenantid(Long tenantid) {
		this.tenantid = tenantid;
	}

	public String getJobstatus() {
		return jobstatus;
	}

	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}

	public Long getJobinfoid() {
		return jobinfoid;
	}

	public void setJobinfoid(Long jobinfoid) {
		this.jobinfoid = jobinfoid;
	}

	public void setErrorinfo(String errorinfo) {
		if (StringUtils.isNotBlank(errorinfo) && errorinfo.length() >= ERROR_SIZE_LIMIT) {
			this.errorinfo = errorinfo.substring(0, ERROR_SIZE_LIMIT);
		} else {
			this.errorinfo = errorinfo;
		}
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
