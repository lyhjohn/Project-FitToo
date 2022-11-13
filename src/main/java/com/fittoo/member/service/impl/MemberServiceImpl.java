package com.fittoo.member.service.impl;

import static com.fittoo.common.message.RegisterErrorMessage.ALREADY_EXIST_USERID;
import static com.fittoo.common.message.RegisterErrorMessage.Pwd_And_RePwd_Not_Equal;
import static com.fittoo.common.message.WithdrawErrorMessage.EXIST_RESERVATION;
import static com.fittoo.reservation.constant.ReservationStatus.COMPLETE;
import static com.fittoo.reservation.constant.ReservationStatus.HOLD;
import static com.fittoo.withdraw_user.constant.WithdrawStatus.HOLD_WITHDRAW;

import com.fittoo.common.message.CommonErrorMessage;
import com.fittoo.common.message.FindErrorMessage;
import com.fittoo.exception.RegisterException;
import com.fittoo.exception.UserIdAlreadyExist;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.exception.WithdrawException;
import com.fittoo.member.entity.Member;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.member.service.MemberService;
import com.fittoo.reservation.entity.Reservation;
import com.fittoo.reservation.repository.ReservationRepository;
import com.fittoo.trainer.model.UpdateInput;
import com.fittoo.withdraw_user.entity.WithdrawUser;
import com.fittoo.withdraw_user.repository.WithdrawUserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	private final WithdrawUserRepository withdrawUserRepository;
	private final JPAQueryFactory queryFactory;
	private final static String MEMBER = "member";

	@Override
	@Transactional
	public void memberRegister(MemberInput input) {
		Optional<Member> optionalMember = memberRepository.findByUserId(input.getUserId());
		if (optionalMember.isPresent()) {
			input.setLoginType(MEMBER);
			throw new RegisterException(ALREADY_EXIST_USERID.message(), input, MEMBER,
				new UserIdAlreadyExist());
		}

		if (!input.getPassword().equals(input.getRepassword())) {
			input.setLoginType(MEMBER);
			throw new RegisterException(Pwd_And_RePwd_Not_Equal.message(), input, MEMBER);
		}

		String encPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

		Member member = Member.of(input, encPassword);

		// 스레드 동시 접근으로 위 중복체크가 뚫릴 시 예외처리로 걸러낸다.
		try {
			memberRepository.saveAndFlush(member);
		} catch (DataIntegrityViolationException e) {
			input.setLoginType(MEMBER);
			throw new RegisterException(ALREADY_EXIST_USERID.message(), input, MEMBER, e);
		}
	}

	@Override
	@Transactional
	public MemberDto findMember(String userId) {
		Optional<Member> optionalMember = memberRepository.findByUserId(userId);

		return optionalMember.map(MemberDto::of).orElse(null);
	}

	@Transactional
	@Override
	public MemberDto update(UpdateInput input) {
		Member member = memberRepository.findByUserId(input.getUserId())
			.orElseThrow(
				() -> new UserNotFoundException(FindErrorMessage.NOT_FOUND_USER.message()));

		member.update(input);

		return MemberDto.of(member);
	}

	@Override
	@Transactional
	public void withdraw(String userId) {
		Member member = memberRepository.findByUserId(userId)
			.orElseThrow(
				() -> new UserNotFoundException(CommonErrorMessage.NOT_FOUND_USER.message()));

		checkExistReservation(member);
		withdrawUserRepository.save(new WithdrawUser(userId, member.getLoginType()));
	}

	private void checkExistReservation(Member member) {
		Optional<List<Reservation>> optionalReservation = reservationRepository.findByMember(
			member);
		optionalReservation.ifPresent(reservations -> reservations.forEach(x -> {
			if (x.getReservationStatus().equals(COMPLETE) || x.getReservationStatus()
				.equals(HOLD)) {
				throw new WithdrawException(EXIST_RESERVATION.message());
			}
		}));
	}

	@Transactional
	@Override
	public boolean existWithdrawUser(String userId) {
		Optional<WithdrawUser> optionalWithdrawUser = withdrawUserRepository.findById(userId);
		if (optionalWithdrawUser.isEmpty()) {
			return false;
		}

		WithdrawUser withdrawUser = optionalWithdrawUser.get();
		if (withdrawUser.getStatus().equals(HOLD_WITHDRAW)) {
			withdrawUserRepository.delete(withdrawUser);
			return false;
		}

		return true;
	}

	@Override
	@Transactional
	public void completeWithdraw(String userId) {
		memberRepository.deleteByUserId(userId);
	}
}
