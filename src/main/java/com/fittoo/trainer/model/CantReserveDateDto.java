package com.fittoo.trainer.model;

import com.fittoo.trainer.entity.CantReserveDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CantReserveDateDto {

	private LocalDate date;
//	private boolean cantReserve;


	@Builder
	public CantReserveDateDto(LocalDate date) {
		this.date = date;
	}

	public static CantReserveDateDto of(CantReserveDate cantReserveDate) {
		return CantReserveDateDto.builder()
			.date(cantReserveDate.getDate())
			.build();
	}

	public static List<CantReserveDateDto> of(List<CantReserveDate> list) {
		List<CantReserveDateDto> dtoList = new ArrayList<>();

		for (CantReserveDate date : list) {
			dtoList.add(CantReserveDateDto.of(date));
		}
		return dtoList;
	}
}
