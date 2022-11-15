package com.fittoo.web.service.impl;

import static com.fittoo.common.message.LoginErrorMessage.INVALID_ID_OR_PWD;

import com.fittoo.common.message.LoginErrorMessage;
import com.fittoo.exception.LoginFailException;
import com.fittoo.member.entity.Member;
import com.fittoo.member.model.LoginType;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.repository.TrainerRepository;
import com.fittoo.web.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);


        if (optionalTrainer.isPresent()) {
            return loginValidTrainer(optionalTrainer.get());
        }

        if (optionalMember.isPresent()) {
            return loginValidMember(optionalMember.get());
        }


        throw new UsernameNotFoundException(INVALID_ID_OR_PWD.message());
    }


    @Transactional
    public UserDetails loginValidMember(Member member) {

        if (!member.getLoginType().equals(LoginType.NORMAL)) {
            throw new LoginFailException(INVALID_ID_OR_PWD);
        }
        // 회원 ROLE 추가
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }

    @Transactional
    public UserDetails loginValidTrainer(Trainer trainer) {

        if (!trainer.getLoginType().equals(LoginType.TRAINER)) {
            throw new LoginFailException(INVALID_ID_OR_PWD);
        }
        // 회원 ROLE 추가
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_TRAINER"));

        return new User(trainer.getUserId(), trainer.getPassword(), grantedAuthorities);
    }
}

