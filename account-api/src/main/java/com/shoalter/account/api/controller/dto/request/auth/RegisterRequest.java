package com.shoalter.account.api.controller.dto.request.auth;

import lombok.Value;

@Value
public class RegisterRequest {

	String username;

	String password;
}
