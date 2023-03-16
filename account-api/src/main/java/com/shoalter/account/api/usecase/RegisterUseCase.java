package com.shoalter.account.api.usecase;

import com.shoalter.account.api.config.security.service.TokenUserService;
import com.shoalter.account.api.controller.dto.response.auth.RegisterResponse;
import com.shoalter.account.api.dao.entity.AccountEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.exception.ClientErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegisterUseCase {

	private final AccountRepository accountRepository;

	private final PasswordEncoder passwordEncoder;

	private final TokenUserService tokenUserService;

	public RegisterResponse start(String username, String password) {
		if (accountRepository.findByUsername(username).isPresent()) {
			throw new ClientErrorException("username.duplicated", username);
		}
		return Optional.of(createUser(username, password))
				.map(this::convertToResponse)
				.orElseThrow();
	}

	private AccountEntity createUser(String username, String password) {
		var entity = new AccountEntity();
		entity.setUsername(username);
		entity.setPassword(passwordEncoder.encode(password));
		return accountRepository.save(entity);
	}

	private RegisterResponse convertToResponse(AccountEntity newAccount) {
		String refreshToken = generateRefreshToken(newAccount);
		return new RegisterResponse(
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
