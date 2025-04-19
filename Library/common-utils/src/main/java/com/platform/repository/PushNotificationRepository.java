package com.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.platform.entity.PushNotificationToken;

/**
 * @author muhil
 */
@Repository
public interface PushNotificationRepository extends JpaRepository<PushNotificationToken, Long> {

	String findPushNotificationByTokenQuery = "select pnt from PushNotificationToken pnt where pnt.token=:token and pnt.userid=:userid";

	@Query(findPushNotificationByTokenQuery)
	PushNotificationToken findPushNotificationByToken(@Param("token") String token, @Param("userid") Long userid);

	String findPushNotificationTokensForUserQuery = "select pnt from PushNotificationToken pnt where pnt.token=:token and pnt.userid=:userid";

	@Query(findPushNotificationTokensForUserQuery)
	List<PushNotificationToken> findPushNotificationTokensForUser(@Param("userid") Long userid);

}
