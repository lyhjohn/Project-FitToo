package com.fittoo.member.controller;

import com.fittoo.member.entity.Member;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.member.service.MemberService;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.repository.TrainerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberControllerTest {

	@Autowired
	MemberService memberService;

	@Test
	@Commit
	void saveTest() {
	    //given

		MemberInput memberInput = new MemberInput();
		memberInput.setPassword("1234");
		memberInput.setGender(1);
		memberInput.setAddress("test");
		memberInput.setUserId("testId");
		memberInput.setPhoneNumber("phone");
		memberInput.setExercisePeriod("3년");
		memberInput.setUserName("홍길동");
		memberInput.setRegPurposeList(Arrays.asList("123","456"));
		memberService.memberRegister(memberInput);
	    //when
		MemberDto testId = memberService.findMember("testId");
		//then
		assertThat(testId.getGender()).isEqualTo("남자");
	}

}