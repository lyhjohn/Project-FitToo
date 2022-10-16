package com.fittoo.web.service.impl;

import com.fittoo.member.entity.Member;
import com.fittoo.member.model.LoginType;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.repository.TrainerRepository;
import com.fittoo.web.model.LoginInput;
import com.fittoo.web.model.LoginInfo;
import com.fittoo.web.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public LoginInfo loginSubmit(LoginInput input) {
        return loginValid(input);
    }


    @Transactional
    public LoginInfo loginValid(LoginInput input) {
        if (input.getLoginType().equals("member")) {
            Optional<Member> optionalMember = memberRepository.findByUserId(input.getLoginId());
            if (optionalMember.isEmpty()) {
                return null;
            }
            Member member = optionalMember.get();
            if (!member.getPassword().equals(input.getPassword())) {
                return null;
            }
            if (!member.getLoginType().equals(LoginType.NORMAL)) {
                return null;
            }

            return optionalMember.map(LoginInfo::of).orElse(null);
        }

        if (input.getLoginType().equals("trainer")) {
            Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(input.getLoginId());
            if (optionalTrainer.isEmpty()) {
                return null;
            }
            Trainer trainer = optionalTrainer.get();
            if (!trainer.getPassword().equals(input.getPassword())) {
                return null;
            }
            if (!trainer.getLoginType().equals(LoginType.TRAINER)) {
                return null;
            }
            return optionalTrainer.map(LoginInfo::of).orElse(null);
        }
        return null;
    }
}

