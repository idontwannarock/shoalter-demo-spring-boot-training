package com.shoalter.account.api.config.security.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class AuthenticatedUser implements Serializable {

	private static final long serialVersionUID = -5419292384744402073L;

	private Integer userId;
	private String username;
}
