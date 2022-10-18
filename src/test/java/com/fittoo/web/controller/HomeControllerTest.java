package com.fittoo.web.controller;

import com.fittoo.member.model.MemberDto;
import com.fittoo.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HomeControllerTest {

    @Autowired
    MemberService memberService;

    @Test
    void loginHomeTest() {
        String id = "1111";

        MemberDto member = memberService.findMember(id);
        System.out.println("member.getUserId() = " + member.getUserId());
    }

}