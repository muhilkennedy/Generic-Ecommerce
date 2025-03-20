package com.user.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.platform.entity.BaseEntity;
import com.platform.logging.Log;
import com.platform.social.LoginTypes;
import com.user.dao.EmployeeDaoService;
import com.user.dao.UserHashDaoService;
import com.user.entity.Employee;
import com.user.entity.EmployeeRole;
import com.user.entity.User;
import com.user.entity.UserHash;
import com.user.exceptions.UserException;
import com.user.service.EmployeeService;

/**
 * @author muhil 
 */
@Service
@Qualifier("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDaoService employeeDaoService;
	
	@Autowired
	private UserHashDaoService userHashDaoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		String generatedPassword = "devPassword";//SecurityUtil.generateRandomPassword();
		Log.user.debug(
				String.format("Generated password for user {%s} is {%s}", employee.getEmailid(), generatedPassword));
		employee.setPassword(StringUtils.isAllBlank(user.getPassword()) ? passwordEncoder.encode(generatedPassword)
				: passwordEncoder.encode(user.getPassword()));
		employee.setLoginType(LoginTypes.INTERNAL.name());
		employee = (Employee) employeeDaoService.saveAndFlush(employee);
		UserHash hash = new UserHash();
		hash.setMobile(user.getMobile());
		hash.setUniqueName(employee.getUniqueName());
		userHashDaoService.save(hash);
		//send onboard email here
		return employee;
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
	public Employee updateEmployeeRoles(Employee employee, List<EmployeeRole> ers) {
	    employee.setEmployeeRoles(ers);
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

}
