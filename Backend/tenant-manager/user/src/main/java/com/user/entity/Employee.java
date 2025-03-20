package com.user.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.BasePermission;
import com.platform.user.permissions.Permissions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "EMPLOYEE")
@ClassMetaProperty(code = "EMP")
@Indexed(index = "employee_index")
public class Employee extends User implements BasePermission {

	private static final long serialVersionUID = 2L;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "REPORTSTO")
	private Long reportsto;
	
	//@PIIData(allowedRolePermissions = { Permissions.ADMIN, Permissions.MANAGE_USERS })
	@Column(name = "SECONDARYEMAIL")
	private String secondaryemail;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<EmployeeRole> employeeRoles;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private EmployeeInfo employeeInfo;
	
	public Employee() {
		super();
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Long getReportsto() {
		return reportsto;
	}

	public void setReportsto(Long reportsto) {
		this.reportsto = reportsto;
	}

	public List<EmployeeRole> getEmployeeRoles() {
		return employeeRoles;
	}

	public void setEmployeeRoles(List<EmployeeRole> employeeeRoles) {
		this.employeeRoles = employeeeRoles;
	}

	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}
	
	public String getSecondaryemail() {
		return secondaryemail;
	}

	public void setSecondaryemail(String secondaryemail) {
		this.secondaryemail = secondaryemail;
	}

	@PrePersist
	private void prePesist() {
		if (StringUtils.isAllBlank(this.secondaryemail)) {
			setSecondaryemail(this.getEmailid());
		}
	}
	
	@Override
	protected void generateUniqueName() {
		setUniquename("EMP-".concat(UUID.randomUUID().toString()));
	}
	
	@Override
	public Set<Permissions> getUserPermissions() {
		Set<Permissions> permissions = new HashSet<Permissions>();
		CollectionUtils.emptyIfNull(employeeRoles).stream().forEach(role -> role.getRole().getPermissions().stream()
				.forEach(rp -> permissions.add(Permissions.getPermissionIfValid(rp.getPermission().getPermission()))));
		return permissions;
	}

}
