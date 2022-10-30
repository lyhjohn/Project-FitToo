package com.fittoo.member.service.impl;

import static com.fittoo.common.message.ErrorMessage.ALREADY_EXIST_USERID;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.exception.UserIdAlreadyExist;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.member.entity.Member;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.MemberUpdateInput;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.member.service.MemberService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;


	@Override
	@Transactional
	public ServiceResult memberRegister(MemberInput input) {
		Optional<Member> optionalMember = memberRepository.findByUserId(input.getUserId());
		if (optionalMember.isPresent()) {
			throw new UserIdAlreadyExist(ALREADY_EXIST_USERID.message(), input);
//			return new ServiceResult(false, ALREADY_EXIST_USERID);
		}

		String encPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

		Member member = Member.of(input, encPassword);
		memberRepository.save(member);

		return new ServiceResult();
	}

	@Override
	@Transactional
	public MemberDto findMember(String userId) {
		Optional<Member> optionalMember = memberRepository.findByUserId(userId);

		return optionalMember.map(MemberDto::of).orElse(null);
	}

	@Transactional
	@Override
	public void update(MemberUpdateInput input, String userId) {
		Optional<Member> optionalMember = memberRepository.findByUserId(userId);
		if (optionalMember.isEmpty()) {
			throw new UserNotFoundException(ErrorMessage.NOT_FOUND_USER.message());
		}

		Member member = optionalMember.get();
		member.update(input);
	}
}
