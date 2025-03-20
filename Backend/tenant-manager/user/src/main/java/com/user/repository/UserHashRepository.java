package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.UserHash;

/**
 * @author muhil
 */
@Repository
public interface UserHashRepository extends JpaRepository<UserHash, Long> {
	
	String findByUniqueNameQuery = "select usr from UserHash usr where uniquename=:uniqueName";

	@Query(findByUniqueNameQuery)
	UserHash findByUniqueName(@Param("uniqueName") String uniqueName);

}
