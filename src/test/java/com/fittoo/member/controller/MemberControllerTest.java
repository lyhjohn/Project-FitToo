package com.fittoo.member.controller;

import com.fittoo.member.entity.Member;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberControllerTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TrainerRepository trainerRepository;

    @Test
    @Transactional
    @Commit
    void setRegPurposeTest() {
        //given
        List<String> regPurposeList = Arrays.asList("diet", "weight", "재활", "health", "partner_training");
        //when
        String regPurposes = Member.setRegPurpose(regPurposeList);
        //then
        assertThat(regPurposes).isEqualTo("diet,weight,재활,health,partner_training");
    }




//    @Test
//    @Transactional
//    @Commit
//    void setMainPtListTest() {
//        //given
//        Trainer trainer = new Trainer();
//        String[] strs = new String[]{"diet", "weight", "재활", "health", "partner"};
//
//        //when
//        trainer.setMainPtList(strs);
//        trainerRepository.save(trainer);
//        Optional<Trainer> findTrainer = trainerRepository.findById(trainer.getId());
//        //then
////        assertThat(findMember.get().getRegPurpose())
//        System.out.println(findTrainer.get().getMainPtList());
//
//    }


}