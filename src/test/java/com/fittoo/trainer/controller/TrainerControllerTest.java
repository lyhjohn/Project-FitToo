package com.fittoo.trainer.controller;

import com.fittoo.trainer.entity.ExerciseType;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.repository.ExerciseTypeRepository;
import com.fittoo.trainer.service.TrainerService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@Transactional
class TrainerControllerTest {

	@Autowired
	ExerciseTypeRepository exerciseTypeRepository;

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
		trainer.setExerciseType("yoga");
		trainer.setProfilePicture(new MultipartFile() {
			@Override
			public String getName() {
				return "test";
			}

			@Override
			public String getOriginalFilename() {
				return "test";
			}

			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public boolean isEmpty() {
				return false;
			}

			@Override
			public long getSize() {
				return 5;
			}

			@Override
			public byte[] getBytes() throws IOException {
				return new byte[5];
			}

			@Override
			public InputStream getInputStream() throws IOException {
				return null;
			}

			@Override
			public void transferTo(File dest) throws IOException, IllegalStateException {

			}
		});
		trainerService.trainerRegister(trainer);
	}

	@Test
	void exerciseTypeTest() {
	    //given
		Optional<ExerciseType> optionalExerciseType = exerciseTypeRepository.findById("yoga");
		ExerciseType exerciseType = optionalExerciseType.get();
		//when
		List<Trainer> trainerList = exerciseType.getTrainerList();
		//then
		System.out.println("trainerList = " + trainerList.get(0).getUserId());
		System.out.println("trainerList = " + trainerList.get(0).getPrice());
		System.out.println("trainerList = " + trainerList.size());


	}

}