package com.fittoo.reservation.repository;

import com.fittoo.member.entity.Member;
import com.fittoo.reservation.entity.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Optional<Reservation> findByTrainerUserIdAndDateAndStartTimeAndEndTime(String trainerUserId,
		LocalDate date, String startTime, String endTime);

	List<Reservation> findAllByDate(LocalDate date);

	Optional<Reservation> findByMemberUserIdAndId(String memberUserId, Long id);
	boolean existsByMemberUserIdAndId(String memberUserId, Long id);

	Optional<List<Reservation>> findByMember(Member member);

	void deleteByMemberUserId(String memberUserId);
}
