package com.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.platform.entity.ConfigType;

/**
 * @author Muhil
 *
 */
@Repository
public interface ConfigTypeRepository extends JpaRepository<ConfigType, Long> {
	
	String findConfigsByTypeQuery = "select config from ConfigType config where type=:type";

	@Query(findConfigsByTypeQuery)
	List<ConfigType> findConfigsByType(@Param("type") String type);

}
