package com.fittoo.trainer.model;

import com.fittoo.trainer.entity.Schedule;
import com.fittoo.utills.DeduplicationUtils;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScheduleDto {


	private String comment;

	private LocalDate date;
	private int personnel;
	private LocalTime startTime;
	private LocalTime endTime;
	private String exercise;
	private String trainerId;
	private long price;

	@Builder
	public ScheduleDto(String trainerId, String comment, LocalDate date, int personnel, LocalTime startTime,
		LocalTime endTime, String exercise, long price) {
		this.comment = comment;
		this.date = date;
		this.personnel = personnel;
		this.startTime = startTime;
		this.endTime = endTime;
		this.exercise = exercise;
		this.trainerId = trainerId;
		this.price = price;
	}



	public static ScheduleDto of(Schedule schedule) {
		return ScheduleDto.builder()
			.comment(schedule.getComment())
			.date(schedule.getDate())
			.personnel(schedule.getPersonnel())
			.startTime(schedule.getStartTime())
			.endTime(schedule.getEndTime())
			.exercise(schedule.getExercise())
			.trainerId(schedule.getTrainerUserId())
			.price(schedule.getPrice())
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
