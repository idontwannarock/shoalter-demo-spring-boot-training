package com.shoalter.account.api.dao.repository;

import com.shoalter.account.api.dao.entity.ClockingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClockingRepository extends JpaRepository<ClockingEntity, Long> {

    Optional<ClockingEntity> findTopByAccountIdOrderByCreateTimeAsc(Integer accountId);

	Optional<ClockingEntity> findTopByAccountIdOrderByCreateTimeDesc(Integer accountId);
}
