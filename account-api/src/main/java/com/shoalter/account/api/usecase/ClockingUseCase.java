package com.shoalter.account.api.usecase;

import com.shoalter.account.api.dao.entity.ClockingEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.dao.repository.ClockingRepository;
import com.shoalter.account.api.exception.ClientErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClockingUseCase {

	private final AccountRepository accountRepository;

	private final ClockingRepository clockingRepository;

	public void start(Integer userId) {
		accountRepository.findById(userId).ifPresentOrElse(account -> {
			var clocking = new ClockingEntity();
			clocking.setAccount(account);
			clockingRepository.save(clocking);
		}, () -> {
			throw new ClientErrorException("account.not.found");
		});
	}
}
