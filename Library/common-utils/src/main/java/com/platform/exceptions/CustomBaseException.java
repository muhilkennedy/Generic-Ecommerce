package com.platform.exceptions;

import java.util.UUID;

/**
 * @author Muhil Extend this class for custom exceptions
 */
public abstract class CustomBaseException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;

	public CustomBaseException() {
		super();
		this.errorCode = UUID.randomUUID().toString();
	}

	public CustomBaseException(String message) {
		super(message);
		this.message = message;
		this.errorCode = UUID.randomUUID().toString();
	}

	public CustomBaseException(String message, String errorCode) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}

	public CustomBaseException(Throwable throwable) {
		super(throwable);
		this.errorCode = UUID.randomUUID().toString();
	}

	public CustomBaseException(String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = UUID.randomUUID().toString();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}