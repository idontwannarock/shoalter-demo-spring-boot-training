package com.shoalter.account.api.schedule;

import com.shoalter.account.api.dao.entity.AccountEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.task.CalculateAccountClockingStatTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClockingStatScheduler {

	private final AccountRepository accountRepository;

	private final CalculateAccountClockingStatTask calculateAccountClockingStatTask;

	private final Executor executor;

	@Scheduled(cron = "0 10 0 * * *")
	public void runClockingStatCalculation() {
		var yesterday = LocalDate.now().minusDays(1);
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (AccountEntity accountEntity : accountRepository.findAll()) {
			futures.add(CompletableFuture.runAsync(
					() -> calculateAccountClockingStatTask.run(accountEntity.getId(), yesterday),
					executor));
		}
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		log.info("Calculate clocking stat of " + yesterday + " job complete.");
	}
}
