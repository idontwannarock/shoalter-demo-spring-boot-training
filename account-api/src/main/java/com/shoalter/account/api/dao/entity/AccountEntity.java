package com.shoalter.account.api.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;

	private String password;

	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createTime;

	@Column(columnDefinition = "DATETIME ON UPDATE CURRENT_TIMESTAMP")
	private LocalDateTime updateTime;
}
