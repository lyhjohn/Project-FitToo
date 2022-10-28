package com.fittoo.member.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.query.Param;

@Getter
@Setter
public class ReservationParam {
    private String trainerId;
    private String year;
    private String currentMonth;
    private String day;
}
