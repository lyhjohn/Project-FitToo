package com.fittoo.trainer.repository;

import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.TrainerInput;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	Optional<Trainer> findByUserId(String userId);

	List<Trainer> findByUserNameContainsAndExerciseType(String trainerName, String exerciseType);
	List<Trainer> findByUserNameContains(String trainerName);

	List<Trainer> findByAddrContains(String address);
}
