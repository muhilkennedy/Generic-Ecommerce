package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.user.entity.EmployeeRole;

/**
 * @author Muhil 
 */
@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long>
{

}
