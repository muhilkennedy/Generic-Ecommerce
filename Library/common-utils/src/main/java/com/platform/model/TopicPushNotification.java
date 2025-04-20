package com.platform.model;

/**
 * @author Muhil
 */
public class TopicPushNotification extends AppNotification {

	private String topic;

	public TopicPushNotification(String topic) {
		super();
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
