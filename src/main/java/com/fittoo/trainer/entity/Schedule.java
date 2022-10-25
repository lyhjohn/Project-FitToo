package com.fittoo.trainer.entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

//	@OneToMany(mappedBy = "schedule")
//	private List<CantReserveDate> cantReserveDateList = new ArrayList<>();

	private String comment;

	//	private LocalDate date;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	private LocalDate excludeDate;

//	public Schedule createSchedule(ScheduleInput input, Trainer trainer) {
//
//		this.comment = input.getComment();
//
//		this.trainer = trainer;
//		trainer.setSchedule(input);
//
//		return this;
//	}

//	public List<CantReserveDate> setCantReserveDate(String start, String end) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		List<CantReserveDate> cantReserveDates = new ArrayList<>();
//		try {
//			Date parseStartDate = df.parse(start);
//			Date parseEndDate = df.parse(end);
//
//			Calendar startCalendar = Calendar.getInstance();
//			Calendar endCalendar = Calendar.getInstance();
//
//			startCalendar.setTime(parseStartDate);
//			endCalendar.setTime(parseEndDate);
//
//			if (startCalendar == endCalendar) {
//				createCantReserveDate(cantReserveDates, startCalendar);
//			} else {
//				while (startCalendar.compareTo(endCalendar) != 0) {
//					createCantReserveDate(cantReserveDates, startCalendar);
//					startCalendar.add(Calendar.DATE, 1);
//				}
//				createCantReserveDate(cantReserveDates, startCalendar);
//			}
//			return cantReserveDates;
//		} catch (ParseException e) {
//			throw new DateParseException(ErrorMessage.INVALID_DATE.message(), e);
//		}
//	}

//	private void createCantReserveDate(List<CantReserveDate> cantReserveDates, Calendar calendar) {
//		CantReserveDate cantReserveDate = new CantReserveDate(calendar); // set date
//		cantReserveDate.setSchedule(this); // set schedule
//		this.cantReserveDateList.add(cantReserveDate);
//		cantReserveDates.add(cantReserveDate);
//	}

	public Schedule(Calendar excludeDate) {
		this.excludeDate = LocalDate.ofInstant(excludeDate.toInstant(), ZoneId.systemDefault());
	}
}
