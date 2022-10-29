package com.fittoo.trainer.model;

import com.fittoo.trainer.entity.ExerciseType;
import com.fittoo.trainer.entity.Trainer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExerciseTypeDto {

	@Id
	private String id;

	@Builder
	public ExerciseTypeDto(String id) {
		this.id = id;
	}

	public static ExerciseTypeDto of(ExerciseType exerciseType) {
		return ExerciseTypeDto.builder()
			.id(exerciseType.getId())
			.build();
	}


	public String getExercise() {
		switch (this.id) {
			case "PT":
				return "헬스 PT";
			case "pilates":
				return "필라테스";
			case "yoga":
				return "요가";
			case "golf":
				return "골프";
			case "pole_dance":
				return "폴댄스";
			case "crossfit":
				return "크로스핏";
			case "rehabilitation":
				return "재활";
			case "boxing":
				return "복싱";
		}
		return "";
	}
}
