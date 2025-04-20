package com.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.entity.PushNotificationTopic;

/**
 * @author muhil
 */
@Repository
public interface PushNotificationTopicRepository extends JpaRepository<PushNotificationTopic, Long> {

}
