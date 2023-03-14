package com.shoalter.account.api.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUserCredentialException extends AuthenticationException {

	private static final long serialVersionUID = -4008026238515410808L;

	public InvalidUserCredentialException(String msg) {
		super(msg);
	}
}
