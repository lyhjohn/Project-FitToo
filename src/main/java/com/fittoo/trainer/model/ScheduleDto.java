package com.fittoo.trainer.model;

import com.fittoo.trainer.entity.Schedule;
import java.util.ArrayList;
import java.util.List;
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

	private TrainerDto trainer;

	@Builder
	public ScheduleDto(Long id, List<CantReserveDateDto> cantReserveDateList, String comment,
		TrainerDto trainer) {
		this.cantReserveDateList = cantReserveDateList;
		this.comment = comment;
		this.trainer = trainer;
	}

	public static ScheduleDto of(Schedule schedule) {
		return ScheduleDto.builder()
			.comment(schedule.getComment())
			.trainer(TrainerDto.of(schedule.getTrainer()))
			.comment(schedule.getComment())
			.build();
	}

}
