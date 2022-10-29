package com.fittoo.trainer.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExerciseType {

	@Id
	private String id;

	@OneToMany(mappedBy = "exerciseType")
	private List<Trainer> trainerList = new ArrayList<>();

	public ExerciseType(String id) {
		this.id = id;
	}

	public void addTrainer(Trainer trainer) {
		trainer.setExerciseType(this);
		this.trainerList.add(trainer);
	}
}
