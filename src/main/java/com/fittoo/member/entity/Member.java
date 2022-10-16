package com.fittoo.member.entity;

import com.fittoo.common.entity.BaseEntity;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.LoginType;
import com.fittoo.reservation.Reservation;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int power; // 3대측정
    private String regPurpose;

    private long point;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservationList = new ArrayList<>();

    public static String setRegPurpose(List<String> regPurposeList) {

        StringBuilder purposeList = new StringBuilder();
        int length = regPurposeList.size();
        for (String regPurpose : regPurposeList) {
            if (--length == 0) {
                purposeList.append(regPurpose);
            } else {
                purposeList.append(regPurpose).append(",");
            }
        }
        return purposeList.toString();
    }

    public static Member of(MemberInput memberInput) {
        return Member.builder()
                .power(memberInput.getPower())
                .userId(memberInput.getUserId())
                .password(memberInput.getPassword())
                .gender(setGender(memberInput.getGender()))
                .loginType(LoginType.NORMAL)
                .phoneNumber(memberInput.getPhoneNumber())
                .exercisePeriod(memberInput.getExercisePeriod())
                .regPurpose(setRegPurpose(memberInput.getRegPurposeList()))
                .build();
    }

    public static String setGender(int num) {
        switch (num) {
            case 1:
                return "남자";
            case 2:
                return "여자";
        }
        return null;
    }


}
