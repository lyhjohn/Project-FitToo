package com.fittoo.common.model;

import com.fittoo.member.model.LoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDto {

    private String userId;
    private String password;
    private String phoneNumber;
    private String gender; // 1=남자 2=여자
    private String region;
    private String userName;

    private LoginType loginType;

    private String exercisePeriod;
    private int power;



}
