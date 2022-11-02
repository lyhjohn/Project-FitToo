package com.fittoo.reservation.service.impl;

import static com.fittoo.common.message.CommonErrorMessage.NOT_FOUND_USER;
import static com.fittoo.common.message.ReservationErrorMessage.EXIST_SAME_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.INVALID_TRAINER_INFO;
import static com.fittoo.member.entity.QMember.member;
import static com.fittoo.reservation.QReservation.reservation;

import com.fittoo.common.message.CommonErrorMessage;
import com.fittoo.common.message.LoginErrorMessage;
import com.fittoo.common.message.ReservationErrorMessage;
import com.fittoo.common.message.ScheduleErrorMessage;
import com.fittoo.exception.ReservationException;
import com.fittoo.exception.UserIdAlreadyExist;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.member.entity.Member;
import com.fittoo.member.entity.QMember;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.reservation.QReservation;
import com.fittoo.reservation.Reservation;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.reservation.repository.ReservationRepository;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.repository.ScheduleRepository;
import com.fittoo.trainer.repository.TrainerRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.ListUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ScheduleRepository scheduleRepository;
	private final MemberRepository memberRepository;
	private final TrainerRepository trainerRepository;
	private final ReservationRepository reservationRepository;
	private final JPAQueryFactory queryFactory;


	@Override
	@Transactional
	public ScheduleDto getSchedule(LocalDate date, String trainerId) {
		Optional<Schedule> optionalSchedule = scheduleRepository.findByDateAndTrainerUserId(date,
			trainerId);

		return optionalSchedule.map(ScheduleDto::of).orElseThrow(() -> new ReservationException(
			ReservationErrorMessage.EMPTY_SCHEDULE.message()));
	}

	@Override
	@Transactional
	public void addReservation(ReservationParam param, String memberId) {

		checkSameReservation(param, memberId);

		Member member = memberRepository.findByUserId(memberId)
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER.message()));

		Trainer trainer = trainerRepository.findByUserId(param.getTrainerId()).orElseThrow(() ->
			new ReservationException(INVALID_TRAINER_INFO.message()));

		Schedule schedule = scheduleRepository.findByDateAndTrainerUserId(param.getDate(),
			param.getTrainerId()).orElseThrow(() -> new ReservationException(
			ScheduleErrorMessage.INVALID_SCHEDULE_INFO.message()));

		Reservation reservation = Reservation.saveReservation(param, trainer, member, schedule);
		reservationRepository.save(reservation);
	}


	public void checkSameReservation(ReservationParam param, String memberId) {

		List<Reservation> list = queryFactory.selectFrom(reservation)
			.where(reservation.trainerUserId.eq(param.getTrainerId())
				.and(reservation.date.eq(param.getDate()))
				.and(reservation.startTime.eq(param.getStartTime()))
				.and(reservation.endTime.eq(param.getEndTime()))
				.and(reservation.member.userId.eq(memberId)))
			.fetch();

		if (!CollectionUtils.isEmpty(list)) {
			throw new ReservationException(EXIST_SAME_RESERVATION.message());
		}
	}

	@Override
	@Transactional
	public List<ReservationDto> getReservationList(String memberId) {

		List<Reservation> reservationList = queryFactory.selectFrom(reservation)
			.where(reservation.member.userId.eq(memberId))
			.fetch();

		if (CollectionUtils.isEmpty(reservationList)) {
			return Collections.emptyList();
		}

		return ReservationDto.of(reservationList);
	}
}
