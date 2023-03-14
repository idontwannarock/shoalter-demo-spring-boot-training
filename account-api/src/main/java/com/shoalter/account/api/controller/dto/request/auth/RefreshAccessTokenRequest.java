package com.shoalter.account.api.controller.dto.request.auth;

import lombok.Data;

@Data
public class RefreshAccessTokenRequest {

	private String refreshToken;
}
