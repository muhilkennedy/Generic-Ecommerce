package com.tenant.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "TENANTDETAILS")
@ClassMetaProperty(code = "TD")
public class TenantDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TENANTID", referencedColumnName = "ROOTID", updatable = false)
	private Tenant tenant;

	@Column(name = "CONTACT")
	private String contact;

	@Column(name = "EMAILID")
	private String emailid;

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;
	
    @Column(name = "STATE")
    private String state;

	@Column(name = "PINCODE")
	private String pincode;

	@Column(name = "BUSINESSEMAIL")
	private String businessemail;

	@Column(name = "TAGLINE")
	private String tagline;
	
    @Column(name = "LOGOTHUMBNAIL")
    private String logothumbnail;

	public TenantDetails() {
		super();
	}

	public TenantDetails(Tenant tenant) {
		super();
		this.tenant = tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getBusinessemail() {
		return businessemail;
	}

	public void setBusinessemail(String businessemail) {
		this.businessemail = businessemail;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getLogothumbnail ()
    {
        return logothumbnail;
    }

    public void setLogothumbnail (String logothumbnail)
    {
        this.logothumbnail = logothumbnail;
    }

}
