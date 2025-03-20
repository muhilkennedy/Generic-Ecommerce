package com.platform.entity;

import com.platform.annotations.ClassMetaProperty;
import com.platform.convertors.AttributeEncryptor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "CONFIGTYPE")
@ClassMetaProperty(code = "CT")
public class ConfigType extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "VALUE")
	@Convert(converter = AttributeEncryptor.class)
	private String value;

	@Column(name = "TYPE")
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
