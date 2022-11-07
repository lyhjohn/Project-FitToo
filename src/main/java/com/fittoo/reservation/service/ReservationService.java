package com.fittoo.reservation.service;

import com.fittoo.member.model.LoginType;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.reservation.model.SearchParam;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.TrainerDto;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
	ScheduleDto getSchedule(LocalDate date, String trainerId);

	void addReservation(ReservationParam param, String memberId);

	List<ReservationDto> getReservationList(String memberId);

	List<TrainerDto> searchTrainer(SearchParam param);

	List<ReservationDto> viewReservationsByMember(ReservationParam param) throws ParseException;

	void confirm(String memberId, Long reservationId);

	void cancel(String memberId, Long reservationId, LoginType cancelByWho);

	void reReservation(Long reservationId);
}
