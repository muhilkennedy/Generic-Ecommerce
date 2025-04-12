package com.tenant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tenant.entity.Tenant;

/**
 * @author Muhil
 *
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

	String getTenantByUniqueNameQuery = "select obj from Tenant obj where obj.uniquename = :uniqueName";

    @EntityGraph(value = "Tenant.detail", type = EntityGraph.EntityGraphType.LOAD)
	@Query(value = getTenantByUniqueNameQuery)
	Tenant findTenantByUniqueName(@Param("uniqueName") String uniqueName);
    
    @EntityGraph(value = "Tenant.detail", type = EntityGraph.EntityGraphType.LOAD)
    List<Tenant> findAll();
	
    String getTenantsCountQuery = "select count(*) from Tenant";

    @Query(value = getTenantsCountQuery, nativeQuery = true)
    Integer findTenantsCount();
    
    String getTenantsCountFromTimeQuery = "select count(*) from Tenant where timecreated >= :timecreated";

    @Query(value = getTenantsCountFromTimeQuery, nativeQuery = true)
    Integer findTenantsCountFromTime(@Param("timecreated") Long timeCreated);

}
