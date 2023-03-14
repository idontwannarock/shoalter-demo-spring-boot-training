package com.shoalter.account.api.dao.mapper;

import com.shoalter.account.api.dao.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface AccountMapper {

	@Select("SELECT * FROM account WHERE username = #{username}")
	@Result(property = "id", column = "id")
	@Result(property = "refreshToken", column = "refresh_token")
	Optional<AccountEntity> findByUsername(String username);

	@Update("UPDATE account SET refresh_token = #{refreshToken} WHERE id = #{accountId}")
	void updateRefreshToken(Integer accountId, String refreshToken);
}
