package com.tenant.entity;

import java.io.Serializable;

import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "TENANT")
@ClassMetaProperty(code = "REALM")
public class Tenant extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "UNIQUENAME", unique = true)
	private String uniquename;

	//@PIIData(allowedRolePermissions = { Permissions.ADMIN, Permissions.SUPER_USER })
	@Column(name = "PARENT")
	private Long parent;
	
	@Column(name = "TIMEZONE")
	private String timezone;
	
	@Column(name = "LOCALE")
	private String locale;
	
	@Column(name = "LOGO")
    private String logo;
	
	@OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private TenantDetails tenantDetail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniquename() {
		return uniquename;
	}

	public void setUniquename(String uniquename) {
		this.uniquename = uniquename;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public TenantDetails getTenantDetail() {
		return tenantDetail;
	}

	public void setTenantDetail(TenantDetails tenantDetail) {
		this.tenantDetail = tenantDetail;
	}
	
	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public String getUniqueName() {
		return uniquename;
	}

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

}
