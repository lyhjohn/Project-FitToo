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

    private int power; // 3대측정
    private String regPurpose;

    private long point;

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
                .power(member.getPower())
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

    public static boolean checkLoginType(MemberDto member, HttpServletRequest request, Model model) {
        if (member == null) {
            model.addAttribute("errorMessage", ErrorMessage.INVALID_ID_OR_PWD.description());
            HttpSession session = request.getSession();
            session.invalidate();
            return false;
        }

        model.addAttribute("member", member);
        return true;
    }


}
