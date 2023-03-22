package com.shoalter.account.api.controller;

import com.shoalter.account.api.controller.dto.request.account.ChangePasswordRequest;
import com.shoalter.account.api.controller.dto.request.auth.LoginRequest;
import com.shoalter.account.api.controller.dto.request.auth.RefreshAccessTokenRequest;
import com.shoalter.account.api.controller.dto.request.auth.RegisterRequest;
import com.shoalter.account.api.controller.dto.response.Response;
import com.shoalter.account.api.controller.dto.response.auth.LoginResponse;
import com.shoalter.account.api.controller.dto.response.auth.RefreshAccessTokenResponse;
import com.shoalter.account.api.controller.dto.response.auth.RegisterResponse;
import com.shoalter.account.api.usecase.ChangePasswordUseCase;
import com.shoalter.account.api.usecase.LoginUseCase;
import com.shoalter.account.api.usecase.RefreshAccessTokenUseCase;
import com.shoalter.account.api.usecase.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {

	private final RegisterUseCase registerUseCase;
	private final LoginUseCase loginUseCase;
	private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;
	private final ChangePasswordUseCase changePasswordUseCase;

	@PostMapping("registration")
	public Response<RegisterResponse> register(@RequestBody RegisterRequest request) {
		log.info("{} registering", request.getUsername());
		return Response.success(registerUseCase.start(request.getUsername(), request.getPassword()));
	}

	@PostMapping("login")
	public Response<LoginResponse> login(@RequestBody LoginRequest request) {
		log.info("{} login", request.getUsername());
		return Response.success(loginUseCase.start(request.getUsername(), request.getPassword()));
	}

	@PostMapping("access-token")
	public Response<RefreshAccessTokenResponse> refreshAccessToken(@RequestBody RefreshAccessTokenRequest request) {
		log.info("refreshing access token with refresh token {}", request.getRefreshToken());
		return Response.success(refreshAccessTokenUseCase.start(request.getRefreshToken()));
	}

	@PatchMapping("{accountId}/password")
	public Response<Void> changePassword(
			@PathVariable Integer accountId,
			@Validated @RequestBody ChangePasswordRequest request) {
		log.info("{} changing password", accountId);
		changePasswordUseCase.start(accountId, request);
		return Response.success(null);
	}
}
