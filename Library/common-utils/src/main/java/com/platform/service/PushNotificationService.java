package com.platform.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.platform.entity.PushNotificationToken;
import com.platform.model.PushNotificationSubscriptionRequest;

/**
 * @author Muhil 
 */
public interface PushNotificationService {

	PushNotificationToken subscribeToPushNotification(PushNotificationSubscriptionRequest request)
			throws FirebaseMessagingException;

	void unSubscribeFromPushNotification(PushNotificationSubscriptionRequest request) throws FirebaseMessagingException;

	void unSubscribeAllPushNotificationForUser(Long userid);

}
