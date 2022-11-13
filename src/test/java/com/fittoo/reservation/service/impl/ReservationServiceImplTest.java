package com.fittoo.reservation.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.member.service.MemberService;
import com.fittoo.reservation.constant.ReservationStatus;
import com.fittoo.reservation.entity.Reservation;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.repository.ScheduleRepository;
import com.fittoo.trainer.repository.TrainerRepository;
import com.fittoo.trainer.service.TrainerService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@Transactional
@Slf4j
class ReservationServiceImplTest {

	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TrainerRepository trainerRepository;
	@Autowired
	MemberService memberService;
	@Autowired
	TrainerService trainerService;
	@Autowired
	ReservationService reservationService;

	private final ReservationParam param1 = new ReservationParam();
	private final ReservationParam param2 = new ReservationParam();

	@BeforeEach
	public void generateEntity() throws ParseException {
		MemberInput member1 = new MemberInput();
		member1.setUserName("userNameA");
		member1.setGender(1);
		member1.setUserId("member1");
		member1.setPassword("1111");
		member1.setRepassword("1111");
		memberService.memberRegister(member1);

		TrainerInput trainer = new TrainerInput();
		trainer.setPrice(50000);
		trainer.setUserName("userA");
		trainer.setGender(1);
		trainer.setUserId("trainer");
		trainer.setPassword("1234");
		trainer.setRepassword("1234");
		trainer.setAddr("addr");
		trainer.setAddrDetail("addr");
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

		ScheduleInput scheduleInput = new ScheduleInput();
		scheduleInput.setPrice(50000)
			.setStartDate(LocalDate.now().toString())
			.setComment("comment")
			.setPersonnel(1)
			.setStartTime("12:00")
			.setEndDate(LocalDate.now().toString())
			.setEndTime("13:00");

		trainerService.createSchedule("trainer", scheduleInput);

		this.param1.setComment("comment")
			.setDate(LocalDate.now())
			.setReservationStatus(ReservationStatus.HOLD)
			.setStartTime("12:00")
			.setEndTime("13:00")
			.setPrice("50000")
			.setTrainerId("trainer")
			.setExercise("yoga")
			.setAddress("addr")
			.setMemberUserId("member1");

		MemberInput member2 = new MemberInput();
		member2.setUserName("userNameB");
		member2.setGender(1);
		member2.setUserId("member2");
		member2.setPassword("1111");
		member2.setRepassword("1111");
		memberService.memberRegister(member2);

		this.param2.setComment("comment")
			.setDate(LocalDate.now())
			.setReservationStatus(ReservationStatus.HOLD)
			.setStartTime("12:00")
			.setEndTime("13:00")
			.setPrice("50000")
			.setTrainerId("trainer")
			.setExercise("yoga")
			.setAddress("addr")
			.setMemberUserId("member2");
	}


	@Test
	void add() throws InterruptedException, ParseException {
		//given

		reservationService.addReservation(param1, "member1");
		List<ReservationDto> member1 = reservationService.getReservationList("member1");
		System.out.println("member1.get(0) = " + member1.get(0));
	}
}