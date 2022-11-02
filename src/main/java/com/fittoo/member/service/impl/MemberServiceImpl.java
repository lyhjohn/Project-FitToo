package com.fittoo.member.service.impl;

import static com.fittoo.common.message.FindErrorMessage.NOT_FOUND_USER;
import static com.fittoo.common.message.RegisterErrorMessage.ALREADY_EXIST_USERID;
import static com.fittoo.common.message.RegisterErrorMessage.Pwd_And_RePwd_Not_Equal;

import com.fittoo.exception.RegisterException;
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
	public void memberRegister(MemberInput input) {
		Optional<Member> optionalMember = memberRepository.findByUserId(input.getUserId());
		if (optionalMember.isPresent()) {
			input.setLoginType("member");
			throw new RegisterException(ALREADY_EXIST_USERID.message(), input, input.getLoginType(),
				new UserIdAlreadyExist());
		}

		if (!input.getPassword().equals(input.getRepassword())) {
			input.setLoginType("member");
			throw new RegisterException(Pwd_And_RePwd_Not_Equal.message(), input,
				input.getLoginType());
		}

		String encPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

		Member member = Member.of(input, encPassword);
		memberRepository.save(member);
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
			throw new UserNotFoundException(NOT_FOUND_USER.message());
		}

		Member member = optionalMember.get();
		member.update(input);
	}
}
