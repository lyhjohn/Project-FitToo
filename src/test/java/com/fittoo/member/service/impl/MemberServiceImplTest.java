package com.fittoo.member.service.impl;

import com.fittoo.member.entity.Member;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Test
    void findMemberTest () {
        //given
        //when
        MemberDto member = memberService.findMember("1111");
        //then
        System.out.println(member.getUserId());
    }
}