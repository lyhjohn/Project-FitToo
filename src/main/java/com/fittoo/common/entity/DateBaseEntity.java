package com.fittoo.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
