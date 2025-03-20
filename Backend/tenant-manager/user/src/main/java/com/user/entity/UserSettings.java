package com.user.entity;

import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.MultiTenantEntity;
import com.user.convertors.UserPreferenceConvertor;
import com.user.model.UserPreferences;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "USERSETTINGS")
@ClassMetaProperty(code = "USRSET")
public class UserSettings extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "UNIQUENAME")
	private String userUniqueName;

	@Column(name = "PREFERENCES", columnDefinition = "jsonb")
    @Convert(converter = UserPreferenceConvertor.class) 
	private UserPreferences preference;

	public String getUserUniqueName() {
		return userUniqueName;
	}

	public void setUserUniqueName(String userUniqueName) {
		this.userUniqueName = userUniqueName;
	}

	public UserPreferences getPreference() {
		return preference;
	}

	public void setPreference(UserPreferences preference) {
		this.preference = preference;
	}

}
