package com.fittoo.trainer.entity;

import com.fittoo.exception.DateParseException;
import com.fittoo.trainer.model.ScheduleInput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "schedule")
	private List<CantReserveDate> cantReserveDateList = new ArrayList<>();

	private String comment;

	@OneToOne(mappedBy = "schedule")
	private Trainer trainer;

	public Schedule createSchedule(ScheduleInput input, Trainer trainer) {

		this.comment = input.getComment();

		this.trainer = trainer;
		trainer.setSchedule(this);

		return this;
	}

	public List<CantReserveDate> setCantReserveDate(String start, String end) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<CantReserveDate> cantReserveDates = new ArrayList<>();
		try {
			Date parseStartDate = df.parse(start);
			Date parseEndDate = df.parse(end);

			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();

			startCalendar.setTime(parseStartDate);
			endCalendar.setTime(parseEndDate);

			if (startCalendar == endCalendar) {
				CantReserveDate cantReserveDate = new CantReserveDate(startCalendar); // set date
				cantReserveDate.setSchedule(this); // set schedule
				this.cantReserveDateList.add(cantReserveDate);
				cantReserveDates.add(cantReserveDate);

			} else {
				while (startCalendar.compareTo(endCalendar) != 0) {
					CantReserveDate cantReserveDate = new CantReserveDate(startCalendar);
					cantReserveDate.setSchedule(this);
					this.cantReserveDateList.add(cantReserveDate);
					startCalendar.add(Calendar.DATE, 1);
					cantReserveDates.add(cantReserveDate);
				}
				CantReserveDate cantReserveDate = new CantReserveDate(startCalendar);
				cantReserveDate.setSchedule(this);
				this.cantReserveDateList.add(cantReserveDate);
				cantReserveDates.add(cantReserveDate);
			}
			return cantReserveDates;
		} catch (ParseException e) {
			throw new DateParseException("올바른 날짜를 입력해주세요", e);
		}
	}
}
