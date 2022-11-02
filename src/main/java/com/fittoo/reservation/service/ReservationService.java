package com.fittoo.reservation.service;

import com.fittoo.member.model.ReservationParam;
import com.fittoo.reservation.Reservation;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.trainer.model.ScheduleDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

public interface ReservationService {
	ScheduleDto getSchedule(LocalDate date, String trainerId);

	void addReservation(ReservationParam param, String memberId);

	List<ReservationDto> getReservationList(String memberId);
}
