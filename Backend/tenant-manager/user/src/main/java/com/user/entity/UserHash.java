package com.user.entity;

import java.security.NoSuchAlgorithmException;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.MultiTenantEntity;
import com.platform.util.EncryptionUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "USERHASH")
@ClassMetaProperty(code = "USRHSH")
@Indexed(index = "userhash_index")
public class UserHash extends MultiTenantEntity {

	private static final long serialVersionUID = -816554745702148307L;
	
	@Column(name = "UNIQUENAME")
	private String uniquename;
	
	@Column(name = "MOBILE")
	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public String getUniqueName() {
		return uniquename;
	}

	public void setUniqueName(String uniqueName) {
		this.uniquename = uniqueName;
	}

	@PrePersist
	public void updateMobileHash() throws NoSuchAlgorithmException {
		setMobile(EncryptionUtil.hash_SHA256(mobile));
	}

}
