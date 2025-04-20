package com.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PUSHNOTIFICATIONTOPIC")
@ClassMetaProperty(code = "PNTOPIC")
public class PushNotificationTopic extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TOKENID", nullable = false)
	private PushNotificationToken token;

	@Column(name = "TOPIC")
	private String topic;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public PushNotificationToken getToken() {
		return token;
	}

	public void setToken(PushNotificationToken token) {
		this.token = token;
	}

}
