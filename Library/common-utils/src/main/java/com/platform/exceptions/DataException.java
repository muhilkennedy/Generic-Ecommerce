package com.platform.exceptions;

/**
 * @author muhil
 *
 */
public class DataException extends CustomBaseRuntimeException {

	private static final long serialVersionUID = 1L;

	public DataException() {
		super();
	}
	
	public DataException(String message) {
		super(message);
	}
}

