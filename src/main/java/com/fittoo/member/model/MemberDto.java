package com.fittoo.member.model;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.member.entity.Member;
import com.fittoo.reservation.Reservation;
import lombok.*;
import org.springframework.ui.Model;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;

    private String regPurpose;

    private long point;

    @Builder.Default // 빌더는 초기화 표현을 무시하므로 Builder.Default 혹은 final로 선언해야함
    private List<Reservation> reservationList = new ArrayList<>();

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
                .regPurpose((member.getRegPurpose()))
                .reservationList(member.getReservationList())
                .regDt(member.getRegDt())
                .udtDt(member.getUdtDt())
                .point(member.getPoint())
                .userName(member.getUserName())
                .build();
    }

    public String getRegDt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

        return regDt != null ? this.regDt.format(formatter) : "";
    }


}
