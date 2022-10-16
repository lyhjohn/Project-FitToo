//package com.fittoo.trainer.service.impl;
//
//import com.fittoo.common.model.ServiceResult;
//import com.fittoo.trainer.entity.Trainer;
//import com.fittoo.trainer.model.TrainerInput;
//import com.fittoo.trainer.repository.TrainerRepository;
//import com.fittoo.trainer.service.TrainerService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class TrainerServiceImplTest {
//
//    @Autowired
//    TrainerService trainerService;
//    @Autowired
//    TrainerRepository trainerRepository;
//
//    @Test
//    void registerTest() {
//        //given
//        TrainerInput trainerInput = new TrainerInput();
//        trainerInput.setAwards("awards1");
//        trainerInput.setGender(1);
//        trainerInput.setPower(100);
//        trainerInput.setPassword("1111");
//        trainerInput.setPrice(10000);
//        trainerInput.setPhoneNumber("010-1111-2222");
//        trainerInput.setExercisePeriod("1년 ~ 2년");
//        trainerInput.setUserId("id123");
//        trainerInput.setProfilePicture("picture1");
//        trainerInput.setMainPtList(Arrays.asList("health", "partner_training"));
//        //when
//        ServiceResult result = trainerService.trainerRegister(trainerInput);
//        Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(trainerInput.getUserId());
//        //then
//        assertThat(result.isResult()).isEqualTo(true);
//        assertThat(optionalTrainer.get().getPassword()).isEqualTo("1111");
//        assertThat(optionalTrainer.get().getMainPtList()).isEqualTo("health,partner_training");
//
//
//    }
//
//}