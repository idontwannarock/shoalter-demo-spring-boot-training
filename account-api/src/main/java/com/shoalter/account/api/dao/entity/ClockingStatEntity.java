package com.shoalter.account.api.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "clocking_stat")
public class ClockingStatEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private AccountEntity account;

	private LocalDateTime clockInTime;

	private LocalDateTime clockOutTime;

	private Long durationInMilli;

	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
			insertable = false, updatable = false)
	private LocalDateTime createTime;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		ClockingStatEntity that = (ClockingStatEntity) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
