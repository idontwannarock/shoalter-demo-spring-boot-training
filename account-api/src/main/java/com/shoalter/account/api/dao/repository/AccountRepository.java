package com.shoalter.account.api.dao.repository;

import com.shoalter.account.api.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

	Optional<AccountEntity> findByUsername(String username);

	Optional<AccountEntity> findByRefreshToken(String refreshToken);
}
