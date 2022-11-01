package com.fittoo.trainer.service.impl;

import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.service.TrainerService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
		trainer.setRePassword("1234");
		trainer.setAddr("address");
		trainer.setAddrDetail("addrDetail");
		trainer.setZipcode("123-456");
		trainer.setExerciseType("yoga");
		trainer.setExercisePeriod("2");
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
	void register() {
	    //given
		TrainerDto trainer = trainerService.findTrainer("trainerA");
		//when & then
		assertThat(trainer.getZipcode()).isEqualTo("123-456");
	}

	@Test
	void createSchedule() {
	    //given
		Time time = new Time(12, 0, 1);
		ScheduleInput scheduleInput = new ScheduleInput();
		scheduleInput.setComment("hello")
			.setPersonnel(5)
			.setStartDate("2022-11-01")
			.setEndDate("2022-11-02")
			.setStartTime(time.toString())
			.setEndTime(time.toString());
	    //when
		trainerService.createSchedule("trainerA", scheduleInput);
		TrainerDto trainer = trainerService.findTrainer("trainerA");
		//then
		assertThat(trainer.getScheduleList().size()).isEqualTo(2);
		List<ScheduleDto> scheduleList = trainer.getScheduleList();
		for (ScheduleDto scheduleDto : scheduleList) {
			System.out.println("scheduleDto = " + scheduleDto);
		}
	}


}