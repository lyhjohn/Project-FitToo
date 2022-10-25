package com.fittoo.trainer.repository;

import com.fittoo.trainer.entity.Trainer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	Optional<Trainer> findByUserId(String userId);
}
