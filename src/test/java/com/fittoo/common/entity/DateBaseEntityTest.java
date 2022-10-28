package com.fittoo.common.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.MemberUpdateInput;
import com.fittoo.member.service.MemberService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DateBaseEntityTest {

	@Autowired
	MemberService memberService;

	@BeforeEach
	void memberSave() {
		MemberInput member = new MemberInput();
		member.setUserName("userNameA");
		member.setGender(1);
		member.setUserId("MemberA");
		member.setPassword("1111");
		member.setRegPurposeList(Arrays.asList("purpose"));
		memberService.memberRegister(member);
	}

	@Test
	void dateSaveTest() {
	    //given
		MemberUpdateInput input = new MemberUpdateInput();
		input.setUserName("userNameB");
		input.setGender(2);
		input.setUserId("MemberB");
		input.setRegPurposeList(Arrays.asList("purpose"));

		memberService.update(input, "MemberA");
		MemberDto member = memberService.findMember("MemberB");
		//when & then
		System.out.println("member = " + member);
		System.out.println("member.getRegDt() = " + member.getRegDt());
		System.out.println("member.getUdtDt() = " + member.getUdtDt());
	}
}