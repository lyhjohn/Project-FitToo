package com.fittoo.trainer.service.impl;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.entity.Member;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.repository.TrainerRepository;
import com.fittoo.trainer.service.TrainerService;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TrainerServiceImplTest {

	@Autowired
	TrainerService trainerService;
	@Autowired
	TrainerRepository trainerRepository;

	@Autowired
	EntityManager em;

	@BeforeEach
	public void setData() {
		Trainer trainer = new Trainer();
		trainer.setPrice(50000);
		trainer.setUserName("userA");
		trainerRepository.save(trainer);
	}



}