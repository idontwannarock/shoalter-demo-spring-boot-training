package com.shoalter.account.api.controller.dto.response.auth;

import lombok.Value;

import java.io.Serializable;

@Value
public class RefreshAccessTokenResponse implements Serializable {

	private static final long serialVersionUID = 5172604182951252520L;

	String accessToken;
}
