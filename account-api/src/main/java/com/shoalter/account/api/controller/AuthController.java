package com.shoalter.account.api.controller;

import com.shoalter.account.api.controller.dto.request.auth.LoginRequest;
import com.shoalter.account.api.controller.dto.request.auth.RefreshAccessTokenRequest;
import com.shoalter.account.api.controller.dto.request.auth.RegisterRequest;
import com.shoalter.account.api.controller.dto.response.Response;
import com.shoalter.account.api.controller.dto.response.auth.LoginResponse;
import com.shoalter.account.api.controller.dto.response.auth.RefreshAccessTokenResponse;
import com.shoalter.account.api.controller.dto.response.auth.RegisterResponse;
import com.shoalter.account.api.usecase.LoginUseCase;
import com.shoalter.account.api.usecase.RefreshAccessTokenUseCase;
import com.shoalter.account.api.usecase.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {

	private final RegisterUseCase registerUseCase;
	private final LoginUseCase loginUseCase;
	private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

	@PostMapping("registration")
	public Response<RegisterResponse> register(@RequestBody RegisterRequest request) {
		return Response.success(registerUseCase.start(request.getUsername(), request.getPassword()));
	}

	@PostMapping("login")
	public Response<LoginResponse> login(@RequestBody LoginRequest request) {
		return Response.success(loginUseCase.start(request.getUsername(), request.getPassword()));
	}

	@PostMapping("access-token")
	public Response<RefreshAccessTokenResponse> refreshAccessToken(@RequestBody RefreshAccessTokenRequest request) {
		return Response.success(refreshAccessTokenUseCase.start(request.getRefreshToken()));
	}
}
