package com.shoalter.account.api.config.security.service;

import com.shoalter.account.api.config.security.pojo.AuthenticatedUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserAuthorityService {

	public Collection<? extends GrantedAuthority> getAuthorities(AuthenticatedUser user) {
		return List.of();
	}
}
