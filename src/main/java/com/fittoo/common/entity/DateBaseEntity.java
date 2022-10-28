package com.fittoo.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class DateBaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime regDt;

	@LastModifiedDate
	private LocalDateTime udtDt;

//	@PrePersist // 영속화 이전
//	public void prePersist() {
//		LocalDateTime now = LocalDateTime.now();
//		regDt = now;
//		udtDt = now;
//	}
//
//	@PreUpdate // 업데이트 이전
//	public void preUpdate() {
//		udtDt = LocalDateTime.now();
//	}

}
