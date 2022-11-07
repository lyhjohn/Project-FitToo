package com.fittoo.reservation.model;

import com.fittoo.reservation.Reservation;
import com.fittoo.reservation.constant.ReservationStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDto {

	private Long id;
	private String trainerUserId;
	private String trainerName;
	private ReservationStatus reservationStatus;

	private String memberUserId;
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
		String comment, int curPersonnel, String memberUserId, ReservationStatus reservationStatus, Long id) {
		this.trainerUserId = trainerUserId;
		this.id = id;
		this.reservationStatus = reservationStatus;
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
		this.memberUserId = memberUserId;
	}

	public static ReservationDto of(Reservation reservation) {
		return ReservationDto.builder()
			.id(reservation.getId())
			.trainerUserId(reservation.getTrainerUserId())
			.reservationStatus(reservation.getReservationStatus())
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
			.memberUserId(reservation.getMemberUserId())
			.build();
	}

	public static List<ReservationDto> fromList(List<Reservation> reservations) {
		List<ReservationDto> list = new ArrayList<>();
		for (Reservation reservation : reservations) {
			list.add(ReservationDto.of(reservation));
		}
		return list;
	}
}
