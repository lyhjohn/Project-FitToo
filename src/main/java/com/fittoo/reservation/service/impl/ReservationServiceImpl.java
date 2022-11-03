package com.fittoo.reservation.service.impl;

import static com.fittoo.common.message.CommonErrorMessage.NOT_FOUND_USER;
import static com.fittoo.common.message.ReservationErrorMessage.EXIST_SAME_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.INVALID_TRAINER_INFO;
import static com.fittoo.common.message.ScheduleErrorMessage.EMPTY_SCHEDULE;
import static com.fittoo.reservation.QReservation.reservation;
import static com.fittoo.trainer.entity.QTrainer.trainer;

import com.fittoo.common.message.ReservationErrorMessage;
import com.fittoo.common.message.ScheduleErrorMessage;
import com.fittoo.exception.ReservationException;
import com.fittoo.exception.ScheduleException;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.member.entity.Member;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.reservation.Reservation;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.reservation.model.SearchParam;
import com.fittoo.reservation.repository.ReservationRepository;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.repository.ScheduleRepository;
import com.fittoo.trainer.repository.TrainerRepository;
import com.fittoo.utills.CalendarUtil.StringOrIntegerToLocalDate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

		Schedule schedule = scheduleRepository.findByDateAndTrainerUserId(date, trainerId);

		return ScheduleDto.of(schedule);
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
			param.getTrainerId());

		Reservation reservation = Reservation.saveReservation(param, trainer, member, schedule,
			memberId);
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

		return ReservationDto.fromList(reservationList);
	}

	@Override
	public List<TrainerDto> searchTrainer(SearchParam param) {
		PageRequest pageRequest = PageRequest.of(0, 5, Direction.ASC, "userName");
		List<Trainer> trainerList = searchBySearchTypeAndExerciseType(param, pageRequest);

		return TrainerDto.of(trainerList);
	}

	public List<Trainer> searchBySearchTypeAndExerciseType(SearchParam param, Pageable pageable) {
		if (param.getExerciseType().equals("all")) {
			if (param.getSearchType().equals("address")) {
				return queryFactory
					.selectFrom(trainer)
					.where(trainer.addr.contains(param.getSearchWord()))
					.offset(pageable.getOffset())
					.limit(pageable.getPageSize())
					.fetch();
			}

			if (param.getSearchType().equals("trainerName")) {
				return queryFactory
					.selectFrom(trainer)
					.where(trainer.userName.contains(param.getSearchWord()))
					.offset(pageable.getOffset())
					.limit(pageable.getPageSize())
					.fetch();
			}
			return Collections.emptyList();
		}

		if (param.getSearchType().equals("address")) {
			return queryFactory
				.selectFrom(trainer)
				.where(trainer.userName.contains(param.getSearchWord())
					.and(trainer.exerciseType.id.eq(param.getExerciseType())))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		}

		if (param.getSearchType().equals("trainerName")) {
			return queryFactory
				.selectFrom(trainer)
				.where(trainer.userName.contains(param.getSearchWord())
					.and(trainer.exerciseType.id.eq(param.getExerciseType())))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		}
		return Collections.emptyList();
	}

	@Override
	public List<ReservationDto> viewReservationsByMember(ReservationParam param)
		throws ParseException {
		int year = param.getYear();
		int month = param.getCurrentMonth();
		int day = param.getDay();

		if (day == -1) {
			throw new ScheduleException(EMPTY_SCHEDULE.message());
		}

		LocalDate date = StringOrIntegerToLocalDate.parseDate(year, month, day);

		List<Reservation> reservationList = reservationRepository.findAllByDate(date);
		if (ListUtils.isEmpty(reservationList)) {
			return Collections.emptyList();
		}

		return ReservationDto.fromList(reservationList);
	}
}
