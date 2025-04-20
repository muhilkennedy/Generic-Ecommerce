package com.platform.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.platform.entity.PushNotificationToken;
import com.platform.model.DirectPushNotification;
import com.platform.model.PushNotificationSubscriptionRequest;
import com.platform.model.TopicPushNotification;

/**
 * @author Muhil 
 */
public interface PushNotificationService {

	PushNotificationToken subscribeToPushNotification(PushNotificationSubscriptionRequest request)
			throws FirebaseMessagingException;

	void unSubscribeFromPushNotification(PushNotificationSubscriptionRequest request) throws FirebaseMessagingException;

	void unSubscribeAllPushNotificationForUser(Long userid);

	void sendNotificationToTarget(DirectPushNotification notification);

	void sendNotificationToTarget(TopicPushNotification notification);

}
