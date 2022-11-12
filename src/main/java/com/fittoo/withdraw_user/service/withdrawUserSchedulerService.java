package com.fittoo.withdraw_user.service;

import static com.fittoo.withdraw_user.constant.WithdrawStatus.COMPLETE_WITHDRAW;
import static com.fittoo.withdraw_user.constant.WithdrawStatus.HOLD_WITHDRAW;
import static com.fittoo.withdraw_user.entity.QWithdrawUser.withdrawUser;

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

	@Scheduled(cron = "0 0 0 0 0/6 *")
	@Transactional
	public void updateWithdrawStatus() {
		List<WithdrawUser> list = queryFactory.selectFrom(withdrawUser)
			.where(withdrawUser.status.eq(HOLD_WITHDRAW))
			.fetch();

		list.forEach(x -> {
			if (LocalDateTime.now().getMonthValue() - x.getApplyDate().getMonthValue() >= 6) {
				x.updateStatus();
			}
		});
	}

	@Scheduled(cron = "0/5 * * * * *")
	@Transactional
	public void completeWithdraw() {
		List<WithdrawUser> list = queryFactory.selectFrom(withdrawUser)
			.where(withdrawUser.status.eq(COMPLETE_WITHDRAW))
			.fetch();

		withdrawUserRepository.deleteAll(list);
	}
}
