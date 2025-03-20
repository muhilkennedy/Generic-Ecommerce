package com.platform.entity;

import java.io.Serializable;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

import com.platform.hibernate.configuration.MultiTenantEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Muhil
 *
 */
@MappedSuperclass
@EntityListeners(MultiTenantEntityListener.class)
@FilterDef(name = "tenantFilter", parameters = { @ParamDef(name = "tenantid", type = Long.class) })
@Filter(name = "tenantFilter", condition = "tenantid = :tenantid")
public class MultiTenantEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String KEY_TENANTID = "tenantid";

	@GenericField
	@Column(name = "TENANTID", updatable = false, nullable = false)
	private Long tenantid;

	public Long getTenantid() {
		return tenantid;
	}

	public void setTenantid(Long tenantid) {
		this.tenantid = tenantid;
	}

}
