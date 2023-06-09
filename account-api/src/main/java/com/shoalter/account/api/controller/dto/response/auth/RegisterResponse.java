package com.shoalter.account.api.controller.dto.response.auth;

import lombok.Value;

import java.io.Serializable;

@Value
public class RegisterResponse implements Serializable {

	private static final long serialVersionUID = 7227232633415633505L;

	String accessToken;
	String refreshToken;
}
