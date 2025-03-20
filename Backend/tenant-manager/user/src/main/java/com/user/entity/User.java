package com.user.entity;

import java.sql.SQLException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;
import com.platform.convertors.AttributeEncryptor;
import com.platform.entity.MultiTenantEntity;
import com.platform.util.PlatformUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

/**
 * @author Muhil Kennedy
 *
 */
@MappedSuperclass
@ClassMetaProperty(code = "USR")
public class User extends MultiTenantEntity{

	private static final long serialVersionUID = 1L;
	
	public static String KEY_FNAME="fname";
	public static String KEY_LNAME="lname";
	public static String KEY_EMAILID="emailid";

	@FullTextField
	@Column(name = "UNIQUENAME", updatable = false)
	private String uniquename;

	@FullTextField
	@Column(name = "FNAME")
	private String fname;
	
	@FullTextField
	@Column(name = "LNAME")
	private String lname;

	//@PIIData(allowedRolePermissions = {Permissions.ADMIN, Permissions.MANAGE_USERS}, visibleCharacters = 4)
	@Column(name = "MOBILE")
	@Convert(converter = AttributeEncryptor.class)
	private String mobile;

	@FullTextField
	//@PIIData(allowedRolePermissions = {Permissions.ADMIN, Permissions.MANAGE_USERS})
	@Column(name = "EMAILID")
	private String emailid;

	@JsonIgnore
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "LOCALE")
	private String locale;

	@Column(name = "TIMEZONE")
	private String timezone;
	
	@Column(name = "LOGINTYPE")
	private String loginType;
	
	public User() {
		super();
	}

	public String getUniquename() {
		return uniquename;
	}

	public void setUniquename(String uniquename) {
		this.uniquename = uniquename;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	@Override
	public String getUniqueName() {
		return uniquename;
	}

	@PrePersist
	private void preProcess() throws SQLException {
		if (StringUtils.isBlank(timezone)) {
			this.timezone = "IST";
		}
		if (StringUtils.isBlank(locale)) {
			this.locale = "en";
		}
		if (StringUtils.isEmpty(uniquename)) {
			generateUniqueName();
		}
	}

	public boolean isSystemUser() {
		return getRootid() == PlatformUtil.SYSTEM_USER_ROOTID;
	}
	
	protected void generateUniqueName() {
		setUniquename("USR-".concat(UUID.randomUUID().toString()));
	}

}
