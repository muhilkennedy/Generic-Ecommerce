package com.user.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.hibernate.search.HibernateSearchService;
import com.platform.logging.Log;
import com.platform.model.SearchFilterDTO;
import com.platform.social.LoginTypes;
import com.platform.util.SecurityUtil;
import com.user.dao.EmployeeDaoService;
import com.user.dao.UserHashDaoService;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.EmployeeRole;
import com.user.entity.User;
import com.user.entity.UserHash;
import com.user.exceptions.UserException;
import com.user.model.EmployeeRequest;
import com.user.service.EmployeeService;

import jakarta.transaction.Transactional;

/**
 * @author muhil 
 */
@Service
@Qualifier("EmployeeService")
@Primary
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDaoService employeeDaoService;
	
	@Autowired
	private UserHashDaoService userHashDaoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private HibernateSearchService hibernateSearch;

	@Override
	public BaseEntity findById(Long rootId) {
		return employeeDaoService.findById(rootId);
	}
	
	@Override
	public User findByUniqueName(String uniqueName) {
		return employeeDaoService.findByUniqueName(uniqueName);
	}
	
	@Override
	public User findByEmailId(String emailId) {
		return employeeDaoService.findByEmailId(emailId);
	}

	@Override
	public Page<?> findAll(Pageable pageable) {
		return employeeDaoService.findAll(pageable);
	}

	@Override
	public User register(User user) {
		Employee employee = (Employee) user;
		String mobile = user.getMobile();
		String generatedPassword = SecurityUtil.generateRandomPassword();
		Log.user.debug(
				String.format("Generated password for user {%s} is {%s}", employee.getEmailid(), generatedPassword));
		employee.setPassword(StringUtils.isAllBlank(user.getPassword()) ? passwordEncoder.encode(generatedPassword)
				: passwordEncoder.encode(user.getPassword()));
		employee.setLoginType(LoginTypes.INTERNAL.name());
		employee = (Employee) employeeDaoService.saveAndFlush(employee);
		// we need to do this for encrypted fields and later query for the same base on hash value
		UserHash hash = new UserHash();
		hash.setMobile(mobile);
		hash.setEmail(user.getEmailid());
		hash.setUniqueName(employee.getUniqueName());
		userHashDaoService.save(hash);
		//send onboard email here
		return employee;
	}
	
	@Override
	public Employee createEmployee (EmployeeRequest request) {
		Employee employee = new Employee();
		employee.setFname(request.getFname());
		employee.setLname(request.getLname());
		employee.setMobile(request.getMobile());
		employee.setEmailid(request.getEmailid());
		employee.setSecondaryemail(request.getSecondaryemail());
		employee.setLocale(request.getLocale());
		employee.setLoginType(LoginTypes.INTERNAL.name());
		employee.setDesignation(request.getDesignation());
		employee.setReportsto(request.getReportsto());
		employee = (Employee) register(employee);
		EmployeeInfo info = new EmployeeInfo();
		info.setEmployee(employee);
		info.setDob(request.getDob());
		info.setGender(request.getGender());
		info.setProfilepic(request.getProfilepicurl());
		info.setProofFileId(request.getProoffileid());
		employee.setEmployeeInfo(info);
		return (Employee) employeeDaoService.save(employee);
	}

	@Override
	public User login(User user) throws UserException {
		Employee employee;
		try {
			employee = (Employee) employeeDaoService.findUserForLogin(user);
		} catch (NoSuchAlgorithmException e) {
			throw new UserException();
		}
		if(employee == null) {
			throw new UserException("Invalid User");
		}
		if (!employee.isActive()) {
			throw new UserException("Inactive User");
		}
		if (employee.getLoginType().equalsIgnoreCase(LoginTypes.INTERNAL.name())
				&& !passwordEncoder.matches(user.getPassword(), employee.getPassword())) {
			throw new UserException("Invalid Password");
		}
		return employee;
	}
	
	@Override
	@Transactional
	public Employee updateEmployeeRoles(Employee employee, List<EmployeeRole> ers) {
	    employee.getEmployeeRoles().clear();
	    employeeDaoService.saveAndFlush(employee);
	    employee.getEmployeeRoles().addAll(ers);
	    return (Employee)employeeDaoService.save(employee);
	}

	@Override
	public User findBySecondaryEmailId(String emailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User toggleStatus(Long rootId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initiatePasswordReset(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(User user, String password, String otp) throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activateAccount(User user, String password, String otp) throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User updateProfilePicture(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateLocale(String langCode) {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
	public User updatePermissions() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<User> searchByName(String keyword) {
		return (List<User>) hibernateSearch.search(Employee.class, keyword, Employee.KEY_FNAME, Employee.KEY_LNAME);
	}
	
	@Override
	public SearchResult<?> searchEmployeesBasedonFilters(List<SearchFilterDTO> filters, int pageSize, int pageNumber, String sortFiled, String sortOrder) {
		return hibernateSearch.advancedSearch(Employee.class, filters, pageSize, pageNumber, sortFiled, sortOrder);
	}
	

}
