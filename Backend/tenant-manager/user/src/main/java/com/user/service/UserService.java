package com.user.service;

import java.io.File;
import java.io.IOException;

import com.platform.service.BaseService;
import com.user.entity.User;
import com.user.exceptions.UserException;

/**
 * @author muhil 
 */
public interface UserService extends BaseService {

	User register(User user);

	User login(User user) throws UserException;

	User findByEmailId(String emailId);
	
	User findBySecondaryEmailId(String emailId);

	User findByUniqueName(String uniqueName);

	User toggleStatus(Long rootId);

	void initiatePasswordReset(User user);

	void resetPassword(User user, String password, String otp) throws UserException;
	
	void activateAccount(User user, String password, String otp) throws UserException;;
	
	User updateProfilePicture(File file) throws IOException;
	
	void updateLocale(String langCode);
	
	default User updateSecondaryEmail(String email) {
		return null;
	}

}
