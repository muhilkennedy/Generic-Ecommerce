package com.user.repository;

import org.springframework.data.jpa.repository.EntityGraph;
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

	@EntityGraph(value = "Employee.detail", type = EntityGraph.EntityGraphType.LOAD)
	@Query(findByEmailQuery)
	Employee findByEmailId(@Param("emailId") String emailId);
	
	String findBySecondaryEmailQuery = "select emp from Employee emp where secondaryemail=:emailId";

	@Query(findBySecondaryEmailQuery)
	Employee findBySecondaryEmail(@Param("emailId") String emailId);

	String findByUniqueNameQuery = "select emp from Employee emp where uniquename=:uniqueName";

	@EntityGraph(value = "Employee.detail", type = EntityGraph.EntityGraphType.LOAD)
	@Query(findByUniqueNameQuery)
	Employee findByUniqueName(@Param("uniqueName") String uniqueName);
	
	String findByEmailOrMobileQuery = "select emp from Employee emp where emailid=:emailId";

	@EntityGraph(value = "Employee.detail", type = EntityGraph.EntityGraphType.LOAD)
	@Query(findByEmailOrMobileQuery)
	Employee findByEmailOrMobile(@Param("emailId") String emailId, @Param("mobile") String mobile);
	
	String findEmployeeForLoginQuery = "select emp from Employee emp inner join UserHash uh on emp.uniquename=uh.uniquename where uh.email = :emailOrMobile or uh.mobile = :emailOrMobile";
	
	@EntityGraph(value = "Employee.detail", type = EntityGraph.EntityGraphType.LOAD)
	@Query(findEmployeeForLoginQuery)
	Employee findEmployeeForLogin(@Param("emailOrMobile") String emailOrMobileHash);
	
    String getEmployeesCountQuery = "select count(*) from Employee";

    @Query(value = getEmployeesCountQuery, nativeQuery = true)
    Integer getEmployeesCount();
    
    String getEmployeeCountFromTimeQuery = "select count(*) from Employee where timecreated >= :timecreated";

    @Query(value = getEmployeeCountFromTimeQuery, nativeQuery = true)
    Integer getEmployeeCountFromTime(@Param("timecreated") Long timeCreated);

}
