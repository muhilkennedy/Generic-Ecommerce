package com.platform.exceptions;

/**
 * @author muhil
 *
 */
public class InvalidUserPermission extends CustomBaseException {

	private static final long serialVersionUID = 1L;

	public InvalidUserPermission() {
		super();
	}
	
	public InvalidUserPermission(String message) {
		super(message);
	}
}
