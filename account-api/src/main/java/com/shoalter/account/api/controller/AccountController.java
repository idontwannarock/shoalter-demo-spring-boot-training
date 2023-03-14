package com.shoalter.account.api.controller;

import com.shoalter.account.api.controller.dto.request.account.ChangePasswordRequest;
import com.shoalter.account.api.controller.dto.response.Response;
import com.shoalter.account.api.usecase.ChangePasswordUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/accounts")
@RestController
public class AccountController {

	private final ChangePasswordUseCase changePasswordUseCase;

	@PatchMapping("{accountId}/password")
	public Response<Void> changePassword(
			@PathVariable Integer accountId,
			@Validated @RequestBody ChangePasswordRequest request) {
		changePasswordUseCase.start(accountId, request);
		return Response.success(null);
	}
}
