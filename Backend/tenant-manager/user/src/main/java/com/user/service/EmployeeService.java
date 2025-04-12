package com.user.service;

import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;

import com.platform.model.SearchFilterDTO;
import com.user.entity.Employee;
import com.user.entity.EmployeeRole;
import com.user.model.EmployeeRequest;

/**
 * @author Muhil
 */
public interface EmployeeService extends UserService {

    Employee updateEmployeeRoles (Employee employee, List<EmployeeRole> ers);

	Employee createEmployee(EmployeeRequest request);

	SearchResult<?> searchEmployeesBasedonFilters(List<SearchFilterDTO> filters, int pageSize, int pageNumber,
			String sortFiled, String sortOrder);

}
