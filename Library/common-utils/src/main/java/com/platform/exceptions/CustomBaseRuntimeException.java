package com.platform.exceptions;

import java.util.UUID;

/**
 * @author Muhil Extend this class for custom runtime exceptions
 */
public abstract class CustomBaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;

	public CustomBaseRuntimeException() {
		super();
		this.errorCode = UUID.randomUUID().toString();
	}

	public CustomBaseRuntimeException(String message) {
		super(message);
		this.message = message;
		this.errorCode = UUID.randomUUID().toString();
	}

	public CustomBaseRuntimeException(String message, String errorCode) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}

	public CustomBaseRuntimeException(Throwable throwable) {
		super(throwable);
		this.errorCode = UUID.randomUUID().toString();
	}

	public CustomBaseRuntimeException(String message, Throwable throwable) {
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