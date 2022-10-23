package com.fittoo.trainer.model;

import com.fittoo.trainer.entity.CantReserveDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CantReserveDateDto {

	private String date;
//	private boolean cantReserve;

	private ScheduleDto schedule;

	@Builder
	public CantReserveDateDto(String date, ScheduleDto schedule) {
		this.date = date;
		this.schedule = schedule;
	}

	public static CantReserveDateDto of(CantReserveDate cantReserveDate) {
		return CantReserveDateDto.builder()
			.date(cantReserveDate.getDate())
			.schedule(ScheduleDto.of(cantReserveDate.getSchedule()))
			.build();
	}


//	private String getDate() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		return this.date != null ? this.date.format(formatter) : "";
//	}
}
