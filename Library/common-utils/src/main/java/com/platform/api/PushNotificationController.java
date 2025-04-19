package com.platform.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.platform.annotations.ValidateUserToken;
import com.platform.entity.PushNotificationToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.model.PushNotificationSubscriptionRequest;
import com.platform.push.notification.GooglePushNotificationService;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("notification/push")
@ValidateUserToken
public class PushNotificationController {
	
	@Autowired
	private GooglePushNotificationService notificationService;

	@PostMapping(value = "/subscribe", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<PushNotificationToken> subscribeToPushNotification(
			@RequestBody PushNotificationSubscriptionRequest notificationToken) throws FirebaseMessagingException {
		return new GenericResponse<PushNotificationToken>(
				notificationService.subscribeToPushNotification(notificationToken));
	}

	@PostMapping(value = "/unsubscribe", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<?> unSubscribeFromPushNotification(
			@RequestBody PushNotificationSubscriptionRequest notificationToken) throws FirebaseMessagingException {
		notificationService.unSubscribeFromPushNotification(notificationToken);
		return new GenericResponse<>().setStatus(Response.Status.OK);
	}

}