package com.fittoo.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class MemberInput {

    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String phoneNumber;
    private String userName;

    @NotNull
    private int gender; // 1=남자 2=여자

    @NotNull
    private String exercisePeriod;


    @NotNull
    private List<String> regPurposeList;
    private String address;
    private String addrDetail;
    private String zipCode;
}
