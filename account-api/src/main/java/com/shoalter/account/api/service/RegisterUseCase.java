package com.shoalter.account.api.service;

import com.shoalter.account.api.controller.dto.response.auth.RegisterResponse;
import com.shoalter.account.api.dao.entity.AccountEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegisterUseCase {

	private final AccountRepository accountRepository;

	private final PasswordEncoder passwordEncoder;

	public RegisterResponse start(String username, String password) {
		if (accountRepository.findByUsername(username).isPresent()) {
			throw new IllegalArgumentException("Username " + username + " existed");
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
		var response = new RegisterResponse();
		response.setUserId(newAccount.getId());
		return response;
	}
}
