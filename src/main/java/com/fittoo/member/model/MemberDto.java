package com.fittoo.member.model;

import com.fittoo.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.ui.Model;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDto {

    private Long id;

    private long point;

    private String userId;
    private String password;
    private String phoneNumber;
    private String gender; // 1=남자 2=여자

    private LoginType loginType;
    private String userName;

    private String exercisePeriod;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;
    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .password(member.getPassword())
                .gender(member.getGender())
                .loginType(member.getLoginType())
                .phoneNumber(member.getPhoneNumber())
                .exercisePeriod(member.getExercisePeriod())
                .regDt(member.getRegDt())
                .udtDt(member.getUdtDt())
                .point(member.getPoint())
                .userName(member.getUserName())
                .build();
    }

    public String getRegDt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");

        return regDt != null ? this.regDt.format(formatter) : "";
    }

    public String getUdtDt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");

        return udtDt != null ? this.udtDt.format(formatter) : "";
    }

    public static void whatIsGender(Object gender, Model model) {
        if (gender != null) {
            if (gender instanceof String) {
                if (gender.equals("남자")) {
                    model.addAttribute("isMan", true);
                } else {
                    model.addAttribute("isGirl", true);
                }
            }

            if (gender instanceof Integer) {
                if ((int) gender == 1) {
                    model.addAttribute("isMan", true);
                } else {
                    model.addAttribute("isGirl", true);
                }
            }
        }
    }


}
