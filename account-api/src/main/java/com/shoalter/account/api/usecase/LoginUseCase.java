package com.shoalter.account.api.usecase;

import com.shoalter.account.api.config.security.service.TokenUserService;
import com.shoalter.account.api.controller.dto.response.auth.LoginResponse;
import com.shoalter.account.api.dao.entity.AccountEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.exception.InvalidUserCredentialException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginUseCase {

	private final AccountRepository accountRepository;

	private final PasswordEncoder passwordEncoder;

	private final TokenUserService tokenUserService;

	public LoginResponse start(String username, String password) {
		return accountRepository.findByUsername(username)
				.filter(account -> passwordEncoder.matches(password, account.getPassword()))
				.map(this::convertToResponse)
				.orElseThrow(() -> new InvalidUserCredentialException("User not found."));
	}

	private LoginResponse convertToResponse(AccountEntity newAccount) {
		String refreshToken = generateRefreshToken(newAccount);
		return new LoginResponse(
				tokenUserService.toAccessToken(newAccount.getId(), newAccount.getUsername()),
				refreshToken);
	}

	private String generateRefreshToken(AccountEntity newAccount) {
		String refreshToken = tokenUserService.toRefreshToken(newAccount.getId());
		newAccount.setRefreshToken(refreshToken);
		accountRepository.save(newAccount);
		return refreshToken;
	}
}
