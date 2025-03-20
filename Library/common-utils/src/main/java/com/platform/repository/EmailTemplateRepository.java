package com.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.platform.entity.EmailTemplate;

/**
 * @author Muhil
 *
 */
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

	String findTemplateByNameQuery = "select template from EmailTemplate template where title=:title";

	@Query(findTemplateByNameQuery)
	EmailTemplate findTemplateByName(@Param("title") String title);

}
