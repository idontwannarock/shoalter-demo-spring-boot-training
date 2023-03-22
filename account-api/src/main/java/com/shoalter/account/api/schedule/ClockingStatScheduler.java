package com.shoalter.account.api.schedule;

import com.shoalter.account.api.dao.entity.AccountEntity;
import com.shoalter.account.api.dao.entity.ClockingEntity;
import com.shoalter.account.api.dao.entity.ClockingStatEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.dao.repository.ClockingRepository;
import com.shoalter.account.api.dao.repository.ClockingStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class ClockingStatScheduler {

	private final AccountRepository accountRepository;

	private final ClockingRepository clockingRepository;

	private final ClockingStatRepository clockingStatRepository;

	@Scheduled(cron = "0 10 0 * * *")
	public void runClockingStatCalculation() {
		var yesterday = LocalDate.now().minusDays(1);
		for (AccountEntity accountEntity : accountRepository.findAll()) {
			clockingRepository.findTopByAccountIdOrderByCreateTimeAsc(accountEntity.getId())
					.ifPresent(clockIn -> {
						ClockingEntity clockOut = clockingRepository.findTopByAccountIdOrderByCreateTimeDesc(accountEntity.getId()).orElse(new ClockingEntity());
						var stat = new ClockingStatEntity();
						stat.setAccount(accountEntity);
						stat.setClockInTime(clockIn.getCreateTime());
						stat.setClockOutTime(clockOut.getCreateTime());
						stat.setClockingDate(yesterday);
						stat.setDurationInMilli(calculateDurationInMilli(clockIn.getCreateTime(), clockOut.getCreateTime()));
						clockingStatRepository.save(stat);
					});
		}
	}

	private Long calculateDurationInMilli(LocalDateTime startTime, LocalDateTime endTime) {
		if (startTime == null || endTime == null) return 0L;
		return ChronoUnit.MILLIS.between(startTime, endTime);
	}
}
