package com.fittoo.reservation.service;

import com.fittoo.member.model.ReservationParam;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.reservation.model.SearchParam;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.TrainerDto;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
	ScheduleDto getSchedule(LocalDate date, String trainerId);

	void addReservation(ReservationParam param, String memberId);

	List<ReservationDto> getReservationList(String memberId);

	List<TrainerDto> searchTrainer(SearchParam param);
}
