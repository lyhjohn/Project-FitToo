package com.fittoo.trainer.model;

import com.fittoo.member.model.LoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInput {


    private String userId;

    private String password;


    private String phoneNumber;


    private int gender; // 1=남자 2=여자

    private String exercisePeriod;

    private String awards;
    private String userName;

    private String profilePictureNewName;

    private List<String> mainPtList;

    private long price;

    private String introduce;
    private String region;
    private LoginType loginType;


}
