package com.shoalter.account.api.dao.repository;

import com.shoalter.account.api.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
}
