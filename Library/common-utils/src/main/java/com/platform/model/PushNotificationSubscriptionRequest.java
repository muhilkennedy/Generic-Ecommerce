package com.platform.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Muhil
 */
public class PushNotificationSubscriptionRequest {

	private String token;
	private String topic;
	private String deviceInfo;
	private Long userid;

	public List<String> getTokens() {
		if (StringUtils.isNotBlank(token)) {
			return List.of(token);
		}
		return null;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

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

}
