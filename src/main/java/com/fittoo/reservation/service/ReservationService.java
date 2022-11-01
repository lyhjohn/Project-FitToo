package com.fittoo.reservation.service;

import com.fittoo.trainer.model.ScheduleDto;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

public interface ReservationService {
	ScheduleDto getSchedule(LocalDate date, String trainerId);
}
