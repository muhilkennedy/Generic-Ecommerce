package com.platform.exceptions;

/**
 * @author muhil
 */
public class CryptoException extends CustomBaseException {
	
	private static final long serialVersionUID = 1L;

	public CryptoException(String msg) {
		super(msg);
	}

	public CryptoException(Exception ex) {
		super(ex);
	}
}
