package com.fittoo.member.model;

import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class MemberInput {

    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String repassword;

    @NotNull
    @NotEmpty
    private String phoneNumber;
    private String userName;

    @NotNull
    private int gender; // 1=남자 2=여자

    @NotNull
    private String exercisePeriod;

    private String address;
    private String addrDetail;
    private String zipCode;

    private String loginType;
}
