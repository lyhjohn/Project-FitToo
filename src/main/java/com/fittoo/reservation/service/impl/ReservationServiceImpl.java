package com.fittoo.reservation.service.impl;

import com.fittoo.common.message.ReservationErrorMessage;
import com.fittoo.exception.ReservationException;
import com.fittoo.exception.UserIdAlreadyExist;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.repository.ScheduleRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ScheduleRepository scheduleRepository;


	@Override
	public ScheduleDto getSchedule(LocalDate date, String trainerId) {
		Optional<Schedule> optionalSchedule = scheduleRepository.findByDateAndTrainerUserId(date,
			trainerId);

		return optionalSchedule.map(ScheduleDto::of).orElseThrow(() -> new ReservationException(
			ReservationErrorMessage.EMPTY_SCHEDULE.message()));
	}
}
