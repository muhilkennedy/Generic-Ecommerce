package com.platform.entity;

import java.util.List;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PUSHNOTIFICATIONTOKEN")
@ClassMetaProperty(code = "PNT")
@Indexed(index = "push_notification_index")
@NamedEntityGraph(name = "PushNotificationToken.topics", attributeNodes = { @NamedAttributeNode("topics") })
public class PushNotificationToken extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "USERID")
	private Long userid;

	@FullTextField
	@Column(name = "TOKEN")
	private String token;

	@Column(name = "DEVICEINFO")
	private String deviceinfo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "token", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<PushNotificationTopic> topics;
	
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

	public List<PushNotificationTopic> getTopics() {
		return topics;
	}

	public void setTopics(List<PushNotificationTopic> topics) {
		this.topics = topics;
	}
	
}
