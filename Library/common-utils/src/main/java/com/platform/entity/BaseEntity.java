package com.platform.entity;

import java.io.Serializable;

import com.platform.hibernate.configuration.BaseEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Muhil
 *
 */
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String KEY_TIMEUPDATED = "timeupdated";
	public static final String KEY_TIMECREATED = "timecreated";
	public static final String KEY_CREATEDBY = "createdby";
	public static final String KEY_MODIFIEDBY = "modifiedby";
	public static final String KEY_ROOTID = "rootid";
	public static final String KEY_ACTIVE = "active";
	public static final String KEY_VERSION = "version";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROOTID", updatable = false, nullable = false)
	private long rootid;

	@Column(name = "TIMEUPDATED")
	private long timeupdated;

	@Column(name = "TIMECREATED")
	private long timecreated;

	@Column(name = "MODIFIEDBY")
	private long modifiedby;
	
	@Column(name = "CREATEDBY")
	private long createdby;
	
	@Column(name = "VERSION")
	private long version;

	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active;

	public BaseEntity() {
		super();
	}

	public long getRootid() {
		return rootid;
	}

	public void setRootid(long rootId) {
		this.rootid = rootId;
	}

	public long getTimeupdated() {
		return timeupdated;
	}

	public void setTimeupdated(long timeUpdated) {
		this.timeupdated = timeUpdated;
	}

	public long getTimecreated() {
		return timecreated;
	}

	public void setTimecreated(long timeCreated) {
		this.timecreated = timeCreated;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(long modifiedBy) {
		this.modifiedby = modifiedBy;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdBy) {
		this.createdby = createdBy;
	}
	
	// returns rootid by default
	public String getUniqueName() {
		return String.valueOf(rootid);
	}
	
}

