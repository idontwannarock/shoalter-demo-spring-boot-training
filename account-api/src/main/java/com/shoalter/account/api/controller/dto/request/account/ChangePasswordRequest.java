package com.shoalter.account.api.controller.dto.request.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {

	@NotBlank(message = "Given current password must not be blank")
	private String currentPassword;

	@NotBlank(message = "Given new password must not be blank")
	private String newPassword;
}
