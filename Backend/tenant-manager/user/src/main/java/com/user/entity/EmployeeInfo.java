package com.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.MultiTenantEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "EMPLOYEEINFO")
@ClassMetaProperty(code = "EMPINFO")
public class EmployeeInfo extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	//@PIIData(allowedRolePermissions = {Permissions.ADMIN, Permissions.MANAGE_USERS})
	@Column(name = "DOB")
	private String dob;

	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "PROOFBLOBID")
	private Long proofblobid;
	
	@Column(name = "PROFILEPIC")
	private String profilepic;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "EMPLOYEEID", referencedColumnName = "ROOTID", nullable = false, updatable = false)
	private Employee employee;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getProofFileId() {
		return proofblobid;
	}

	public void setProofFileId(Long proofFileId) {
		this.proofblobid = proofFileId;
	}

	public String getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}

}
