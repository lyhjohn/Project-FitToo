package com.fittoo.common.model;

import com.fittoo.member.model.LoginType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class UserBaseDto {

    private String userId;
    private String password;
    private String phoneNumber;
    private String gender; // 1=남자 2=여자
    private String userName;

    private LoginType loginType;

    private String exercisePeriod;



}
