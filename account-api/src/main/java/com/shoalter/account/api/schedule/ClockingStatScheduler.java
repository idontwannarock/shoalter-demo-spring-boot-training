package com.shoalter.account.api.schedule;

import com.shoalter.account.api.dao.repository.ClockingStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClockingStatScheduler {

	private final ClockingStatRepository clockingStatRepository;

	@Scheduled(cron = "0 10 0 * * *")
	public void runClockingStatCalculation() {
		clockingStatRepository.calculateClockingStatOfYesterday();
	}
}
