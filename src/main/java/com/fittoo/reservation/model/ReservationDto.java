package com.fittoo.reservation.model;

import static javax.persistence.FetchType.LAZY;

import com.fittoo.member.entity.Member;
import com.fittoo.member.model.MemberDto;
import com.fittoo.reservation.Reservation;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.TrainerDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDto {

	private String trainerUserId;
	private String trainerName;
	private String address;
	private String exercise;
	private String startTime;
	private String endTime;
	private LocalDate date;
	private String price;
	private String personnel;
	private int curPersonnel;
	private String comment;

	@Builder
	public ReservationDto(String trainerUserId, String trainerName, String address, String exercise,
		String startTime, String endTime, LocalDate date, String price, String personnel,
		String comment, int curPersonnel) {
		this.trainerUserId = trainerUserId;
		this.trainerName = trainerName;
		this.address = address;
		this.exercise = exercise;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
		this.price = price;
		this.personnel = personnel;
		this.comment = comment;
		this.curPersonnel = curPersonnel;
	}

	public static ReservationDto of(Reservation reservation) {
		return ReservationDto.builder()
			.trainerUserId(reservation.getTrainerUserId())
			.trainerName(reservation.getTrainer().getUserName())
			.address(reservation.getAddress())
			.exercise(reservation.getExercise())
			.startTime(reservation.getStartTime())
			.endTime(reservation.getEndTime())
			.date(reservation.getDate())
			.price(reservation.getPrice())
			.personnel(reservation.getPersonnel())
			.comment(reservation.getComment())
			.curPersonnel(reservation.getSchedule().getCurPersonnel())
			.build();
	}

	public static List<ReservationDto> of(List<Reservation> reservations) {
		List<ReservationDto> list = new ArrayList<>();
		for (Reservation reservation : reservations) {
			list.add(ReservationDto.of(reservation));
		}
		return list;
	}
}
