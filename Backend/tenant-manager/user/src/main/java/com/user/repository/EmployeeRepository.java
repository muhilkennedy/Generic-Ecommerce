package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Employee;

/**
 * @author muhil
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	String findByEmailQuery = "select emp from Employee emp where emailid=:emailId";

	@Query(findByEmailQuery)
	Employee findByEmailId(@Param("emailId") String emailId);
	
	String findBySecondaryEmailQuery = "select emp from Employee emp where secondaryemail=:emailId";

	@Query(findBySecondaryEmailQuery)
	Employee findBySecondaryEmail(@Param("emailId") String emailId);

	String findByUniqueNameQuery = "select emp from Employee emp where uniquename=:uniqueName";

	@Query(findByUniqueNameQuery)
	Employee findByUniqueName(@Param("uniqueName") String uniqueName);
	
	String findByEmailOrMobileQuery = "select emp from Employee emp where emailid=:emailId";

	@Query(findByEmailOrMobileQuery)
	Employee findByEmailOrMobile(@Param("emailId") String emailId, @Param("mobile") String mobile);
	
	String findEmployeeForLoginQuery = "select emp from Employee emp inner join UserHash uh on emp.uniquename=uh.uniquename where emp.emailid = :emailId or uh.mobile = :mobileHash";
	
	@Query(findByEmailOrMobileQuery)
	Employee findEmployeeForLogin(@Param("emailId") String emailId, @Param("mobileHash") String mobile);

}
