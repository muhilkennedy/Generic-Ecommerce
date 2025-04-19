package com.platform.entity;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PushNotificationToken")
@ClassMetaProperty(code = "PNT")
public class PushNotificationToken extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "USERID")
	private Long userid;

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "DEVICEINFO")
	private String deviceinfo;
	
	@Column(name = "TOPIC")
	private String topic;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDeviceinfo() {
		return deviceinfo;
	}

	public void setDeviceinfo(String deviceinfo) {
		this.deviceinfo = deviceinfo;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
