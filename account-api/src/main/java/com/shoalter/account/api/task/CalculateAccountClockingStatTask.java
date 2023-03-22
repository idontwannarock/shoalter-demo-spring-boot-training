package com.shoalter.account.api.task;

import com.shoalter.account.api.dao.entity.AccountEntity;
import com.shoalter.account.api.dao.entity.ClockingEntity;
import com.shoalter.account.api.dao.entity.ClockingStatEntity;
import com.shoalter.account.api.dao.repository.AccountRepository;
import com.shoalter.account.api.dao.repository.ClockingRepository;
import com.shoalter.account.api.dao.repository.ClockingStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CalculateAccountClockingStatTask {

	private final AccountRepository accountRepository;

	private final ClockingRepository clockingRepository;

	private final ClockingStatRepository clockingStatRepository;

	public void run(Integer accountId, LocalDate yesterday) {
		accountRepository.findById(accountId).ifPresent(accountEntity ->
				findClockInRecord(accountEntity).ifPresent(clockIn ->
						clockingStatRepository.save(calculateClockingStat(accountEntity, clockIn, yesterday))));
	}

	private Optional<ClockingEntity> findClockInRecord(AccountEntity accountEntity) {
		return clockingRepository.findTopByAccountIdOrderByCreateTimeAsc(accountEntity.getId());
	}

	private ClockingStatEntity calculateClockingStat(AccountEntity accountEntity, ClockingEntity clockIn, LocalDate yesterday) {
		ClockingEntity clockOut = findClockOutRecord(accountEntity);
		var stat = new ClockingStatEntity();
		stat.setAccount(accountEntity);
		stat.setClockInTime(clockIn.getCreateTime());
		stat.setClockOutTime(clockOut.getCreateTime());
		stat.setClockingDate(yesterday);
		stat.setDurationInMilli(calculateDurationInMilli(clockIn.getCreateTime(), clockOut.getCreateTime()));
		return stat;
	}

	private ClockingEntity findClockOutRecord(AccountEntity accountEntity) {
		return clockingRepository.findTopByAccountIdOrderByCreateTimeDesc(accountEntity.getId())
				.orElse(new ClockingEntity());
	}

	private Long calculateDurationInMilli(LocalDateTime startTime, LocalDateTime endTime) {
		if (startTime == null || endTime == null) return 0L;
		return ChronoUnit.MILLIS.between(startTime, endTime);
	}
}
