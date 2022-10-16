package com.fittoo.member.service.impl;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.entity.Member;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public ServiceResult memberRegister(MemberInput memberInput) {
        Optional<Member> optionalMember = memberRepository.findByUserId(memberInput.getUserId());
        if (optionalMember.isPresent()) {
            return new ServiceResult(false, ErrorMessage.ALREADY_EXIST_USERID);
        }

        Member member = Member.of(memberInput);
        memberRepository.save(member);

        return new ServiceResult();
    }


}
