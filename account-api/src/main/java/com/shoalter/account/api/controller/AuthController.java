package com.shoalter.account.api.controller;

import com.shoalter.account.api.controller.dto.request.auth.RegisterRequest;
import com.shoalter.account.api.controller.dto.response.Response;
import com.shoalter.account.api.controller.dto.response.auth.RegisterResponse;
import com.shoalter.account.api.service.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {

	private final RegisterUseCase registerUseCase;

	@PostMapping("registration")
	public Response<RegisterResponse> register(@RequestBody RegisterRequest request) {
		return Response.success(registerUseCase.start(request.getUsername(), request.getPassword()));
	}
}
