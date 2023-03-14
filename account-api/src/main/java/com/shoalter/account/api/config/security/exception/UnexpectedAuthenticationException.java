package com.shoalter.account.api.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UnexpectedAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 348589923348751833L;

	public UnexpectedAuthenticationException(String msg) {
		super(msg);
	}
}
