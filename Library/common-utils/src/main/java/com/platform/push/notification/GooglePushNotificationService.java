package com.platform.push.notification;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.platform.dao.PushNotificationTokenDaoService;
import com.platform.entity.BaseTenant;
import com.platform.entity.PushNotificationToken;
import com.platform.logging.Log;
import com.platform.model.DirectPushNotification;
import com.platform.model.PushNotificationSubscriptionRequest;
import com.platform.model.TopicPushNotification;
import com.platform.repository.PushNotificationRepository;
import com.platform.server.BaseSession;
import com.platform.service.PushNotificationService;

/**
 * @author muhil
 */
@Service
public class GooglePushNotificationService implements PushNotificationService {

	@Autowired
	private GooglePushNotificationFactory pushMessageFactory;
	
	@Autowired
	private PushNotificationTokenDaoService daoService;

	public void sendNotificationToTarget(DirectPushNotification notification) {
		Message message = Message.builder()
				// Set the configuration for our web notification
				.setWebpushConfig(
						// Create and pass a WebpushConfig object setting the notification
						WebpushConfig.builder().setNotification(
								// Create and pass a web notification object with the specified title, body, and icon URL
								WebpushNotification.builder().setTitle(notification.getTitle())
										.setBody(notification.getMessage())
										.setIcon(((BaseTenant) BaseSession.getTenant()).getLogo()).build())
								.build())
				// Specify the user to send it to in the form of their token
				.setToken(notification.getTarget()).build();
		pushMessageFactory.message().sendAsync(message);
	}

	public void sendNotificationToTarget(TopicPushNotification notification) {
		Message message = Message.builder()
				.setWebpushConfig(WebpushConfig.builder()
						.setNotification(WebpushNotification.builder().setTitle(notification.getTitle())
								.setBody(notification.getMessage())
								.setIcon(((BaseTenant) BaseSession.getTenant()).getLogo()).build())
						.build())
				// Specify the user to send it to in the form of their token
				.setTopic(notification.getTopic()).build();
		pushMessageFactory.message().sendAsync(message);
	}

	public void subscribeToTopic(PushNotificationSubscriptionRequest request) throws FirebaseMessagingException {
		pushMessageFactory.message().subscribeToTopic(request.getTokens(), request.getTopic());
	}

	public void unSubscribeFromTopic(List<String> tokens, String topic) throws FirebaseMessagingException {
		pushMessageFactory.message().unsubscribeFromTopic(tokens, topic);
	}
	
	@Override
	public PushNotificationToken subscribeToPushNotification(PushNotificationSubscriptionRequest request)
			throws FirebaseMessagingException {
		PushNotificationToken token = daoService.findPushNotificationByToken(request.getToken(), request.getUserid());
		if(token == null) {
			token = new PushNotificationToken();
			token.setToken(request.getToken());
			token.setDeviceinfo(request.getDeviceInfo());
			token.setUserid(request.getUserid());
			this.subscribeToTopic(request);
			daoService.save(token);
		}
		return token;
	}

	@Override
	public void unSubscribeFromPushNotification(PushNotificationSubscriptionRequest request)
			throws FirebaseMessagingException {
		PushNotificationToken token = daoService.findPushNotificationByToken(request.getToken(),
				request.getUserid());
		this.unSubscribeFromPushNotification(request);
		daoService.delete(token);
	}

	@Override
	public void unSubscribeAllPushNotificationForUser(Long userid) {
		List<PushNotificationToken> tokens = daoService.findPushNotificationsForUser(userid);
		PushNotificationSubscriptionRequest request = new PushNotificationSubscriptionRequest();
		CollectionUtils.emptyIfNull(tokens).stream().forEach(token -> {
			request.setTopic(token.getTopic());
			request.setToken(token.getToken());
			try {
				this.unSubscribeFromPushNotification(request);
			} catch (FirebaseMessagingException e) {
				Log.platform.error("Exception while unsubscribing push notification : {} : {}", request, e);
			}
		});
	}

}
