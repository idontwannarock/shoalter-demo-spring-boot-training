package com.shoalter.account.api.usecase;

import com.shoalter.account.api.controller.dto.request.account.ChangePasswordRequest;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.exception.ClientErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChangePasswordUseCase {

	private final AccountRepository accountRepository;

	private final PasswordEncoder passwordEncoder;

	public void start(Integer accountId, ChangePasswordRequest request) {
		accountRepository.findById(accountId).ifPresentOrElse(account -> {
			if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())) {
				throw new ClientErrorException("Given current password is not correct");
			}
			account.setPassword(passwordEncoder.encode(request.getNewPassword()));
			accountRepository.save(account);
		}, () -> {
			throw new ClientErrorException("Account not exist");
		});
	}
}
