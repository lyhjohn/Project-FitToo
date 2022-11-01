package com.fittoo.trainer.entity;

import com.fittoo.utills.CalendarUtil;
import com.fittoo.utills.CalendarUtil.StringToLocalDate;
import com.fittoo.utills.CalendarUtil.StringToLocalTime;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "trainer")
public class Schedule {

	@Id
	@GeneratedValue
	private Long id;

	private String comment;

	private int personnel;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	private String trainerUserId;
	private LocalDate date;

	private LocalTime startTime;
	private LocalTime endTime;
	private String exercise;
	private long price;

	public Schedule(String trainerUserId, String comment, int personnel, String exercise,
		Trainer trainer, Calendar date, String startTime,
		String endTime, long price) throws ParseException {
		this.date = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
		this.comment = comment;
		this.personnel = personnel;
		this.trainer = trainer;
		this.exercise = exercise;
		this.trainerUserId = trainerUserId;
		this.price = price;
		this.startTime = StringToLocalTime.getStartTime(startTime);
		this.endTime = StringToLocalTime.getEndTime(endTime);
	}
}
