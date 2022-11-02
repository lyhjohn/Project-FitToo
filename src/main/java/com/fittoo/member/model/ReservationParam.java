package com.fittoo.member.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.repository.query.Param;

@Getter
@Setter
@ToString
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
    private String personnel;
    private String comment;
}
