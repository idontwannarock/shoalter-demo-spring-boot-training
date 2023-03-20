package com.shoalter.account.api.dao.repository;

import com.shoalter.account.api.dao.entity.ClockingStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClockingStatRepository extends JpaRepository<ClockingStatEntity, Long> {
}
