package com.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.util.JWTUtil;
import com.platform.util.PlatformUtil;
import com.user.entity.User;
import com.user.exceptions.UserException;
import com.user.model.LoginUser;
import com.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author muhil
 */
@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	@Qualifier("EmployeeService")
	private UserService empService;
	
	@PostMapping(value = "/employee/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public User loginUser(@RequestBody LoginUser user, HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws UserException {
		User loggedUser = new User();
		loggedUser.setEmailid(user.getEmailid());
		loggedUser.setMobile(user.getMobile());
		loggedUser.setPassword(user.getPassword());
		loggedUser = empService.login(loggedUser);
		httpResponse.addHeader(PlatformUtil.TOKEN_HEADER,
				JWTUtil.generateToken(loggedUser.getUniquename(), String.valueOf(loggedUser.getRootid()),
						JWTUtil.USER_TYPE_EMPLOYEE, httpRequest.getRemoteAddr(), user.isRememberMe()));
		return loggedUser;
	}

}
