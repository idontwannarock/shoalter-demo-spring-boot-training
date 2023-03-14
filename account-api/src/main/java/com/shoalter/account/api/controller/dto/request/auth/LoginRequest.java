package com.shoalter.account.api.controller.dto.request.auth;

import lombok.Data;

@Data
public class LoginRequest {

	String username;

	String password;
}
