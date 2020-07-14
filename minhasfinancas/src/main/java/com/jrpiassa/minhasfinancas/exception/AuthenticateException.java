package com.jrpiassa.minhasfinancas.exception;

public class AuthenticateException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticateException(String message) {
		super(message);
	}
}
