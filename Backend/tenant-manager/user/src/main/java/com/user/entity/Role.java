package com.user.entity;

import java.util.List;

import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.MultiTenantEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "ROLE")
@ClassMetaProperty(code = "ROLE")
public class Role extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "ROLENAME")
	private String rolename;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RolePermission> permissions;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public List<RolePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<RolePermission> permissions) {
		this.permissions = permissions;
	}

}
