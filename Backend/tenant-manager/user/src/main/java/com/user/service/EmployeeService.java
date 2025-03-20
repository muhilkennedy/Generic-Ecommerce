package com.user.service;

import java.util.List;
import com.user.entity.Employee;
import com.user.entity.EmployeeRole;

/**
 * @author Muhil
 */
public interface EmployeeService extends UserService {

    Employee updateEmployeeRoles (Employee employee, List<EmployeeRole> ers);

}
