package com.fittoo.web.model;


import com.fittoo.member.entity.Member;
import com.fittoo.member.model.LoginType;
import com.fittoo.trainer.entity.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    private String userId;

    private String phoneNumber;
    private String gender;

    private String loginType; // member or trainer


    public static LoginInfo of(Member member) {
        return LoginInfo.builder()
                .userId(member.getUserId())
                .loginType(member.getLoginType().description())
                .gender(member.getGender())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

    public static LoginInfo of(Trainer trainer) {
        return LoginInfo.builder()
                .userId(trainer.getUserId())
                .loginType(trainer.getLoginType().description())
                .gender(trainer.getGender())
                .phoneNumber(trainer.getPhoneNumber())
                .build();
    }
}
