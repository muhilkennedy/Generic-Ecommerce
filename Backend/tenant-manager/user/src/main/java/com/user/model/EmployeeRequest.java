package com.user.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Muhil kennedy
 */
public class EmployeeRequest {

	private String fname;
	private String lname;
	private String locale;
	private String designation;
	private String emailid;
	private String secondaryemail;
	private String mobile;
	private Long reportsto;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dob;
	private Long prooffileid;
	private String profilepicurl;
	private String gender;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getSecondaryemail() {
		return secondaryemail;
	}

	public void setSecondaryemail(String secondaryemail) {
		this.secondaryemail = secondaryemail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getReportsto() {
		return reportsto;
	}

	public void setReportsto(Long reportsto) {
		this.reportsto = reportsto;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Long getProoffileid() {
		return prooffileid;
	}

	public void setProoffileid(Long prooffileid) {
		this.prooffileid = prooffileid;
	}

	public String getProfilepicurl() {
		return profilepicurl;
	}

	public void setProfilepicurl(String profilepicurl) {
		this.profilepicurl = profilepicurl;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
