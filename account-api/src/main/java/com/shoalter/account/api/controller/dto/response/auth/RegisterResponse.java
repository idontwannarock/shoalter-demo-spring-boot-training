package com.shoalter.account.api.controller.dto.response.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterResponse implements Serializable {

	private static final long serialVersionUID = -7511796769919706763L;

	private Integer userId;
}
