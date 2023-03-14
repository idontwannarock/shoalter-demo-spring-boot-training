package com.shoalter.account.api.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidRefreshTokenException extends AuthenticationException {

	private static final long serialVersionUID = 1414024241957416322L;

	public InvalidRefreshTokenException(String message) {
		super(message);
	}
}
