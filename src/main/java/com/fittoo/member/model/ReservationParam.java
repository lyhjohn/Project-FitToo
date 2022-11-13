package com.fittoo.member.model;

import com.fittoo.reservation.constant.ReservationStatus;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ReservationParam {
    private String trainerId;
    private int year;
    private int currentMonth;
    private int day;
    private String exercise;
    private String startTime;
    private String endTime;
    private LocalDate date;
    private String price;
    private int personnel;
    private String comment;
    private int curPersonnel;
    private Long reservationId;
    private String trainerUserId;
    private String trainerName;
    private ReservationStatus reservationStatus;
    private String memberUserId;
    private String address;
}
