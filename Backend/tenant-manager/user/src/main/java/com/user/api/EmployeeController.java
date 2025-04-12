package com.user.api;

import java.io.IOException;
import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.model.SearchFilterDTO;
import com.platform.server.BaseSession;
import com.platform.user.permissions.Permissions;
import com.platform.util.BasicUtil;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.model.EmployeeRequest;
import com.user.service.EmployeeService;

/**
 * @author muhil
 */
@RestController
@RequestMapping("employee")
@ValidateUserToken
public class EmployeeController {

	@Autowired
	@Qualifier("EmployeeService")
	private EmployeeService empService;

	@GetMapping
	public GenericResponse<User> getUser() throws SchedulerException, IOException {
		return new GenericResponse<User>().setData((User) BaseSession.getUser());
	}
	
	@GetMapping("/{empId}")
	public GenericResponse<User> getUserById(@PathVariable("empId") Long employeeRootId) throws SchedulerException, IOException {
		return new GenericResponse<User>().setData((User) empService.findById(employeeRootId));
	}

	@PostMapping
	@UserPermission(values = { Permissions.ADMIN, Permissions.EDIT_USERS })
	public GenericResponse<User> createUser(@RequestBody EmployeeRequest userRequest)
			throws SchedulerException, IOException {
		return new GenericResponse<User>().setData(empService.createEmployee(userRequest));
	}

	@GetMapping("/search")
	public GenericResponse<Employee> getEmployeeTypeahead(@RequestParam("keyword") String keyword)
			throws SchedulerException, IOException {
		return new GenericResponse<Employee>().setDataList(empService.searchByName(keyword));
	}

	@PostMapping("/search/filter") // @RequestHeader("filters") List<SearchFilterDTO> filters,
	public GenericResponse<Page<?>> getAllEmployeesBasedonFilters(@RequestBody List<SearchFilterDTO> filters,
			@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber,
			@RequestParam(value = "sortBy", defaultValue = "timecreated") String sortByField,
			@RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder)
			throws SchedulerException, IOException {
		SearchResult<?> result = empService.searchEmployeesBasedonFilters(filters, pageSize, pageNumber, sortByField,
				sortOrder);
		Page<Employee> page = new PageImpl<Employee>((List<Employee>) result.hits(),
				PageRequest.ofSize(pageSize), result.total().hitCount());
		return new GenericResponse<Page<?>>().setData(page);
	}

	@GetMapping("/all")
	public GenericResponse<Page<?>> getAllEmployees(@RequestParam("pageSize") int pageSize,
			@RequestParam("pageNumber") int pageNumber,
			@RequestParam(value = "sortBy", required = false) String sortByField,
			@RequestParam(value = "sortOrder", required = false) String sortOrder)
			throws SchedulerException, IOException {
		return new GenericResponse<Page<?>>()
				.setData(empService.findAll(BasicUtil.getPageable(sortByField, sortOrder, pageNumber, pageSize)));
	}

}
