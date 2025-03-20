package com.user.exceptions;

import com.platform.exceptions.CustomBaseException;

/**
 * @author Muhil 
 */
public class UserException extends CustomBaseException {

	private static final long serialVersionUID = 1L;
	
	public UserException() {
		super();
	}
	
	public UserException(String message) {
		super(message);
	}

}
