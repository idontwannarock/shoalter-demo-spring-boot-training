package com.shoalter.account.api.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ClientErrorException extends RuntimeException {

	private static final long serialVersionUID = -8036639007641781799L;

	String i18nKey;

	Object[] args;

	public ClientErrorException(String i18nKey) {
		this.i18nKey = i18nKey;
		this.args = new Object[] {};
	}

	public ClientErrorException(String i18nKey, Object... args) {
		this.i18nKey = i18nKey;
		this.args = args;
	}
}
