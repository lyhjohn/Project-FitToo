package com.fittoo.trainer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TrainerInput {


    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String phoneNumber;

    @NotNull
    private int gender; // 1=남자 2=여자

    @NotNull
    private String exercisePeriod;


    private String awards;
    private String userName;

    private MultipartFile profilePicture;

    private String exerciseType;


    private long price;

    private String introduce;

    @NotNull
    private String addr;

    @NotNull
    private String addrDetail;

    @NotNull
    private String zipcode;

    private String repassword;

    private String loginType;

}
