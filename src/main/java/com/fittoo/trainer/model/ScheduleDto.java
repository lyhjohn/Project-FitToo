package com.fittoo.trainer.model;

import com.fittoo.trainer.entity.Schedule;
import com.fittoo.utills.DeduplicationUtils;
import java.time.LocalDate;
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


	private String comment;

	private LocalDate date;

	@Builder
	public ScheduleDto(String comment, LocalDate date) {
		this.comment = comment;
		this.date = date;
	}

	public static ScheduleDto of(Schedule schedule) {
		return ScheduleDto.builder()
			.comment(schedule.getComment())
			.date(schedule.getDate())
			.build();
	}


	public static List<ScheduleDto> of(List<Schedule> scheduleList) {
		List<ScheduleDto> dtoList = new ArrayList<>();

		for (Schedule schedule : scheduleList) {
			dtoList.add(ScheduleDto.of(schedule));
		}

		return DeduplicationUtils.deduplication(dtoList, ScheduleDto::getDate)
			.stream().sorted(Comparator.comparing(ScheduleDto::getDate))
			.collect(Collectors.toList());
	}

}
