package com.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.platform.entity.QuartzJobHistory;

/**
 * @author Muhil
 */
@Repository
public interface QuartzJobHistoryRepository extends JpaRepository<QuartzJobHistory, Long> {
	
	String findJobHistoryQuery = "select job from QuartzJobHistory job where uuid=:uuid";

	@Query(findJobHistoryQuery)
	QuartzJobHistory findJobHistory(@Param("uuid") String uuid);

}
