package com.fittoo.trainer.model;

import com.fittoo.trainer.entity.CantReserveDate;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.utills.DeduplicationUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDto {

	private List<CantReserveDateDto> cantReserveDateList = new ArrayList<>();

	private String comment;


	@Builder
	public ScheduleDto(Long id, List<CantReserveDateDto> cantReserveDateList, String comment) {
		this.cantReserveDateList = cantReserveDateList;
		this.comment = comment;
	}

	public static ScheduleDto of(Schedule schedule) {
		return ScheduleDto.builder()
			.comment(schedule.getComment())
			.comment(schedule.getComment())
			.cantReserveDateList(getCantReserveDateList(schedule.getCantReserveDateList()))
			.build();
	}

	public static List<CantReserveDateDto> getCantReserveDateList(List<CantReserveDate> dateList) {
		List<CantReserveDateDto> dto = CantReserveDateDto.of(dateList);
		return DeduplicationUtils.deduplication(dto, CantReserveDateDto::getDate)
			.stream().sorted(Comparator.comparing(CantReserveDateDto::getDate))
			.collect(Collectors.toList());
	}

}
