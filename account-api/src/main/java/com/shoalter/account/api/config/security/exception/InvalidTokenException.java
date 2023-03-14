package com.shoalter.account.api.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

	private static final long serialVersionUID = 9092011391799440768L;

	public InvalidTokenException(String message) {
		super(message);
	}
}
