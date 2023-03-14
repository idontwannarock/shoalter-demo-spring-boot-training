package com.shoalter.account.api.usecase;

import com.shoalter.account.api.config.security.service.TokenUserService;
import com.shoalter.account.api.controller.dto.response.auth.RefreshAccessTokenResponse;
import com.shoalter.account.api.dao.entity.AccountEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshAccessTokenUseCase {

	private final AccountRepository accountRepository;

	private final TokenUserService tokenUserService;

	public RefreshAccessTokenResponse start(String refreshToken) {
		return accountRepository.findByRefreshToken(refreshToken)
				.map(account -> checkRefreshToken(account, refreshToken))
				.map(this::convertToResponse)
				.orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));
	}

	private AccountEntity checkRefreshToken(AccountEntity account, String refreshToken) {
		if (account.getRefreshToken() == null || !account.getRefreshToken().equals(refreshToken)) {
			throw new InvalidRefreshTokenException("Invalid refresh token");
		}
		tokenUserService.isRefreshTokenValid(refreshToken, account.getId());
		return account;
	}

	private RefreshAccessTokenResponse convertToResponse(AccountEntity account) {
		return new RefreshAccessTokenResponse(tokenUserService.toRefreshToken(account.getId()));
	}
}
