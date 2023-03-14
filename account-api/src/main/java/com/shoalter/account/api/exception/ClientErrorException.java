package com.shoalter.account.api.exception;

public class ClientErrorException extends RuntimeException {

	private static final long serialVersionUID = -5991554779328343852L;

	public ClientErrorException(String message) {
		super(message);
	}
}
