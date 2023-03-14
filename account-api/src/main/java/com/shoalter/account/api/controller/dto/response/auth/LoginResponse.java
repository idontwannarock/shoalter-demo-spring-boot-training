package com.shoalter.account.api.controller.dto.response.auth;

import lombok.Value;

import java.io.Serializable;

@Value
public class LoginResponse implements Serializable {

	private static final long serialVersionUID = -751620062349617972L;

	String accessToken;
	String refreshToken;
}
