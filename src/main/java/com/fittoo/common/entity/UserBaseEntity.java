package com.fittoo.common.entity;

import com.fittoo.member.model.LoginType;
import javax.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class UserBaseEntity extends DateBaseEntity {

	@Column(unique = true)
	private String userId;
	private String password;
	private String phoneNumber;
	private String gender; // 1=남자 2=여자
	private String userName;

	@Enumerated(EnumType.STRING)
	private LoginType loginType;

	private String exercisePeriod;
	private String addr;
	private String addrDetail;
	private String zipcode;
}
