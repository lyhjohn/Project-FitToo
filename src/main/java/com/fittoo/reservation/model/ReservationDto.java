package com.fittoo.reservation.model;

import com.fittoo.member.entity.Member;
import com.fittoo.reservation.Reservation;
import com.fittoo.trainer.entity.Trainer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Long id;

    private Trainer trainer;

    private Member member;

    private String address;

    private LocalDateTime reservationDt;

    public static ReservationDto of(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .trainer(reservation.getTrainer())
                .member(reservation.getMember())
                .address(reservation.getAddress())
                .reservationDt(reservation.getReservationDt())
                .build();
    }
}
