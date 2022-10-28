package com.fittoo.trainer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
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

    private List<String> mainPtList;


    private long price;

    private String introduce;

    @NotNull
    private String addr;

    @NotNull
    private String addrDetail;

    @NotNull
    private String zipcode;
}
