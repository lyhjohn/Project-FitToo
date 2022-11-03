package com.fittoo.reservation;

import static javax.persistence.FetchType.LAZY;

import com.fittoo.member.entity.Member;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.trainer.entity.Trainer;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue
	private Long id;

	private String trainerUserId;
	private String memberUserId;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	private String address;
	private String exercise;
	private String startTime;
	private String endTime;
	private LocalDate date;
	private String price;
	private String personnel;
	private String comment;

	@Builder
	public Reservation(String trainerUserId, Schedule schedule, Member member, String address,
		String exercise, String startTime, String endTime,
		LocalDate date, String price, String personnel, String comment, String memberUserId) {
		this.trainerUserId = trainerUserId;
		this.schedule = schedule;
		this.member = member;
		this.address = address;
		this.exercise = exercise;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
		this.price = price;
		this.personnel = personnel;
		this.comment = comment;
		this.memberUserId = memberUserId;
	}

	public static Reservation saveReservation(ReservationParam param, Trainer trainer,
		Member member, Schedule schedule, String memberUserId) {
		Reservation reservation = Reservation.builder()
			.comment(param.getComment())
			.date(param.getDate())
			.personnel(param.getPersonnel())
			.startTime(param.getStartTime())
			.endTime(param.getEndTime())
			.price(param.getPrice())
			.trainerUserId(param.getTrainerId())
			.exercise(param.getExercise())
			.address(trainer.getAddr() + " " + trainer.getAddrDetail())
			.memberUserId(memberUserId)
			.build();

		reservation.saveSchedule(schedule);
		reservation.saveMember(member);
		reservation.saveTrainer(trainer);

		return reservation;
	}

	public void saveTrainer(Trainer trainer) {
		trainer.addReservation(this);
	}

	public void saveMember(Member member) {
		member.addReservation(this);
	}

	public void saveSchedule(Schedule schedule) {
		schedule.addReservation(this);
	}

}
