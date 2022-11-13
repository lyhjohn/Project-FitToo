package com.fittoo.withdraw_user.service;

import static com.fittoo.member.model.LoginType.NORMAL;
import static com.fittoo.member.model.LoginType.TRAINER;
import static com.fittoo.withdraw_user.constant.WithdrawStatus.COMPLETE_WITHDRAW;
import static com.fittoo.withdraw_user.constant.WithdrawStatus.HOLD_WITHDRAW;
import static com.fittoo.withdraw_user.entity.QWithdrawUser.withdrawUser;

import com.fittoo.member.model.LoginType;
import com.fittoo.member.service.MemberService;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.withdraw_user.entity.WithdrawUser;
import com.fittoo.withdraw_user.repository.WithdrawUserRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class withdrawUserSchedulerService {

	private final WithdrawUserRepository withdrawUserRepository;
	private final JPAQueryFactory queryFactory;
	private final MemberService memberService;
	private final TrainerService trainerService;

	@Scheduled(cron = "0 0 12 7 * *")
	@Transactional
	public void updateWithdrawStatus() {
		List<WithdrawUser> list = queryFactory.selectFrom(withdrawUser)
			.where(withdrawUser.status.eq(HOLD_WITHDRAW))
			.fetch();

		list.forEach(x -> {
			if (LocalDateTime.now().getDayOfMonth() - x.getApplyDate().getDayOfMonth() > 7) {
				x.updateStatus();
			}
		});
	}

	@Scheduled(cron = "0 0 12 1 6 *")
	@Transactional
	public void completeWithdraw() {
		List<WithdrawUser> list = queryFactory.selectFrom(withdrawUser)
			.where(withdrawUser.status.eq(COMPLETE_WITHDRAW))
			.fetch();

		list.forEach(x -> {
			if (LocalDateTime.now().getMonthValue() - x.getApplyDate().getMonthValue() >= 6) {

				if (x.getLoginType().equals(NORMAL)) {
					memberService.completeWithdraw(x.getId());

				} else if (x.getLoginType().equals(TRAINER)) {
					trainerService.completeWithdraw(x.getId());
				}
			}
		});

		withdrawUserRepository.deleteAll(list);
	}
}
