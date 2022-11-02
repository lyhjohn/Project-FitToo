package com.fittoo.trainer.repository;

import com.fittoo.trainer.entity.Schedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	Optional<Schedule> findByDateAndTrainerUserId(LocalDate date, String trainerUserId);

	Optional<List<Schedule>> findAllByTrainerUserIdAndDateBetween(String trainerUserId, LocalDate startDate, LocalDate endDate);
}
