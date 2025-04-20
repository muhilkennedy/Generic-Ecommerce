package com.platform.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.entity.PushNotificationToken;
import com.platform.entity.PushNotificationTopic;
import com.platform.repository.PushNotificationRepository;
import com.platform.repository.PushNotificationTopicRepository;
import com.platform.service.BaseDaoService;

/**
 * @author Muhil
 * TODO: scheduled task to remove token for last 6 months.
 */
@Service
public class PushNotificationTokenDaoService implements BaseDaoService {
	
	private final String cacheName = "pushNotification";
	
	@Autowired
	private PushNotificationRepository notificationRespository;
	
	@Autowired
	private PushNotificationTopicRepository topicRepository;

	@Override
	@CachePut(value = cacheName, key = "#obj.rootid")
	public BaseEntity save(BaseEntity obj) {
		return notificationRespository.save((PushNotificationToken)obj);
	}

	@Override
	@CachePut(value = cacheName, key = "#obj.rootid")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return notificationRespository.saveAndFlush((PushNotificationToken)obj);
	}

	@Override
	@Cacheable(value = cacheName, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return notificationRespository.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = cacheName, key = "#obj.rootid")
	public void delete(BaseEntity obj) {
		notificationRespository.delete((PushNotificationToken)obj);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		throw new UnsupportedOperationException();
	}

	@Override
	@CacheEvict(value = cacheName, key = "#rootid")
	public void deleteById(Long rootId) {
		notificationRespository.deleteById(rootId);
	}
	
	@Cacheable(value = cacheName, key = "#token + '-' + #userId")
	public PushNotificationToken findPushNotificationByToken(String token, Long userId) {
		return notificationRespository.findPushNotificationByToken(token, userId);
	}

	public List<PushNotificationToken> findPushNotificationsForUser(Long userId) {
		return notificationRespository.findPushNotificationTokensForUser(userId);
	}
	
	public PushNotificationTopic saveTopic(PushNotificationTopic topic) {
		return topicRepository.save(topic);
	}

}
