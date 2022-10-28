package com.fittoo.trainer.service.impl;

import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TrainerServiceImplTest {

	@Autowired
	TrainerService trainerService;

	@BeforeEach
	public void setData() {
		TrainerInput trainer = new TrainerInput();
		trainer.setPrice(50000);
		trainer.setUserName("userA");
		trainer.setGender(1);
		trainer.setUserId("trainerA");
		trainer.setPassword("1234");
		trainer.setAddr("address");
		trainer.setAddrDetail("addrDetail");
		trainer.setZipcode("123-456");
		trainer.setExercisePeriod("2");
		trainerService.trainerRegister(trainer);
	}

	@Test
	void register() {
	    //given
		TrainerDto trainer = trainerService.findTrainer("trainerA");
		//when & then
		assertThat(trainer.getZipcode()).isEqualTo("123-456");
	}



}