package com.fittoo.member.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.fittoo.member.entity.Member;
import com.fittoo.member.entity.QMember;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.service.MemberService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@SpringBootTest
@Transactional
class MemberServiceImplTest {

	@Autowired
	MemberService memberService;


	@Autowired
	JPAQueryFactory queryFactory;

	@BeforeEach
	void memberSave() {
		MemberInput member = new MemberInput();
		member.setUserName("userNameA");
		member.setGender(1);
		member.setUserId("MemberA");
		member.setPassword("1111");
		memberService.memberRegister(member);
	}

	@Test
	void findMemberTest() {
	    //give
		Member member = queryFactory
			.selectFrom(QMember.member)
			.where(QMember.member.userId.eq("MemberA"))
			.fetchOne();
		//when
		String userName = member.getUserName();
		//then
		assertThat(userName).isEqualTo("userNameA");
	}
}