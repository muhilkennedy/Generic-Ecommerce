package com.platform.entity;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "QRTZ_JOBINFO")
@ClassMetaProperty(code = "QTZJOB")
@Indexed(index = "quartz_index")
public class QuartzJobInfo extends MultiTenantEntity {
	
	private static final long serialVersionUID = 1L;

	@FullTextField
	@Column(name = "JOBNAME")
	private String jobname;

	@Column(name = "JOBGROUP")
	private String jobgroup;

	@Column(name = "ISRECURRING")
	private boolean isrecurring;

	@Column(name = "TENANTID")
	private Long tenantid;

	public QuartzJobInfo() {
		super();
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getJobgroup() {
		return jobgroup;
	}

	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}

	public boolean isIsrecurring() {
		return isrecurring;
	}

	public void setIsrecurring(boolean isrecurring) {
		this.isrecurring = isrecurring;
	}

	public Long getTenantId() {
		return tenantid;
	}

	public void setTenantId(Long tenantId) {
		this.tenantid = tenantId;
	}

}
