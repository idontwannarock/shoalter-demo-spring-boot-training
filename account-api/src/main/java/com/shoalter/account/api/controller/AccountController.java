package com.shoalter.account.api.controller;

import com.shoalter.account.api.config.security.pojo.AuthenticatedUser;
import com.shoalter.account.api.controller.dto.response.Response;
import com.shoalter.account.api.usecase.ClockingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/accounts")
@RestController
public class AccountController {

	private final ClockingUseCase clockingUseCase;

	@PostMapping("clocking")
	public Response<Void> clocking(@AuthenticationPrincipal AuthenticatedUser user) {
		log.info("{} clocking", user.getUserId());
		clockingUseCase.start(user.getUserId());
		return Response.success(null);
	}
}
