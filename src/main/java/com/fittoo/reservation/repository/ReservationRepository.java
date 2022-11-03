package com.fittoo.reservation.repository;

import com.fittoo.reservation.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Optional<Reservation> findByTrainerUserIdAndDateAndStartTimeAndEndTime(String trainerUserId,
		LocalDate date, String startTime, String endTime);

	List<Reservation> findAllByDate(LocalDate date);
}
