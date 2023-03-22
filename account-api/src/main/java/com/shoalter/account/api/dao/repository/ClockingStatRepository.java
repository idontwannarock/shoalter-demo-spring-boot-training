package com.shoalter.account.api.dao.repository;

import com.shoalter.account.api.dao.entity.ClockingStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClockingStatRepository extends JpaRepository<ClockingStatEntity, Long> {

	@Transactional
	@Modifying
	@Query(value =
			"INSERT INTO clocking_stat (account_id, clock_in_time, clock_out_time, duration_in_milli, clocking_date) " +
			"SELECT " +
			"    account_id, " +
			"    MIN(create_time) AS clock_in_time, " +
			"    MAX(create_time) AS clock_out_time, " +
			"    TIMESTAMPDIFF(MICROSECOND, MIN(create_time), MAX(create_time)) / 1000 AS duration_in_milli, " +
			"    CURDATE() AS clocking_date " +
			"FROM clocking " +
			"WHERE create_time >= CURDATE() - INTERVAL 1 DAY AND create_time < CURDATE() " +
			"GROUP BY account_id", nativeQuery = true)
	void calculateClockingStatOfYesterday();
}
