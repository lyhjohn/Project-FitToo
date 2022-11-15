package com.fittoo.reservation.service.impl;

import static com.fittoo.common.message.CommonErrorMessage.NOT_FOUND_USER;
import static com.fittoo.common.message.ReservationErrorMessage.ALREADY_CANCELED_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.ALREADY_COMPLETED_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.ALREADY_END_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.CAN_NOT_COMPLETE_BEFORE_RESERVATION_DATE;
import static com.fittoo.common.message.ReservationErrorMessage.EMPTY_SCHEDULE;
import static com.fittoo.common.message.ReservationErrorMessage.EXIST_SAME_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.INVALID_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.INVALID_TRAINER_INFO;
import static com.fittoo.common.message.ReservationErrorMessage.ONLY_COMPLETE_STATUS_CAN_BE_END;
import static com.fittoo.constant.SearchType.ADDRESS;
import static com.fittoo.constant.SearchType.ALL_TYPE;
import static com.fittoo.constant.SearchType.TRAINER_NAME;
import static com.fittoo.reservation.constant.ReservationStatus.CANCEL;
import static com.fittoo.reservation.constant.ReservationStatus.COMPLETE;
import static com.fittoo.reservation.constant.ReservationStatus.END;
import static com.fittoo.reservation.entity.QReservation.reservation;
import static com.fittoo.trainer.entity.QTrainer.trainer;

import com.fittoo.exception.ReservationException;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.member.entity.Member;
import com.fittoo.member.model.LoginType;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.reservation.entity.Reservation;
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
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ReservationServiceImpl implements ReservationService {

	private final ScheduleRepository scheduleRepository;
	private final MemberRepository memberRepository;
	private final TrainerRepository trainerRepository;
	private final ReservationRepository reservationRepository;
	private final JPAQueryFactory queryFactory;


	@Override
	@Transactional
	public ScheduleDto getSchedule(LocalDate date, String trainerId) {

		Schedule schedule = scheduleRepository.findByDateAndTrainerUserId(date, trainerId)
			.orElseThrow(() -> new ReservationException(EMPTY_SCHEDULE));

		return ScheduleDto.of(schedule);
	}

	@Override
	@Transactional
	public void addReservation(ReservationParam param, String memberId) {

		checkSameReservation(param, memberId);

		Member member = memberRepository.findByUserId(memberId)
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));

		Trainer trainer = trainerRepository.findByUserId(param.getTrainerId()).orElseThrow(() ->
			new ReservationException(INVALID_TRAINER_INFO));

		Schedule schedule = scheduleRepository.findByDateAndTrainerUserId(param.getDate(),
			param.getTrainerId()).orElseThrow(() -> new ReservationException(
			INVALID_RESERVATION));

		Reservation reservation = Reservation.saveReservation(param, trainer, member, schedule,
			memberId);

		reservationRepository.save(reservation);
	}

	@Override
	@Transactional
	public void reReservation(Long reservationId) {
		reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationException(
				EXIST_SAME_RESERVATION)).reReservation();
	}

	public void checkSameReservation(ReservationParam param, String memberId) {

		Long count = queryFactory.select(reservation.count())
			.from(reservation)
			.where(reservation.trainerUserId.eq(param.getTrainerId())
				.and(reservation.date.eq(param.getDate()))
				.and(reservation.startTime.eq(param.getStartTime()))
				.and(reservation.endTime.eq(param.getEndTime()))
				.and(reservation.member.userId.eq(memberId)))
			.fetchOne();

		if (count > 0) {
			throw new ReservationException(EXIST_SAME_RESERVATION);
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

		return queryFactory
			.selectFrom(trainer)
			.where(searchValueEq(param))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private BooleanExpression searchValueEq(SearchParam param) {

		if (param.getSearchWord() == null) {
			return null;
		}

		String exerciseType = param.getExerciseType();
		String searchType = param.getSearchType();
		String searchWord = param.getSearchWord();

		if (exerciseType.equals(ALL_TYPE.search()) && searchType.equals(ADDRESS.search())) {
			return trainer.addr != null ? trainer.addr.contains(searchWord) : null;
		}

		if (exerciseType.equals(ALL_TYPE.search()) && searchType.equals(TRAINER_NAME.search())) {
			return trainer.userName != null ? trainer.userName.contains(searchWord) : null;
		}

		if (searchType.equals(ADDRESS.search())) {
			return trainer.addr != null ? trainer.exerciseType.id.eq(exerciseType)
				.and(trainer.addr.contains(searchWord)) : null;
		}

		if (searchType.equals(TRAINER_NAME.search())) {
			return trainer.userName != null ? trainer.exerciseType.id.eq(exerciseType)
				.and(trainer.addr.contains(searchWord)) : null;
		}
		return null;
	}

	@Override
	public List<ReservationDto> viewReservationsByMember(ReservationParam param)
		throws ParseException {
		int year = param.getYear();
		int month = param.getCurrentMonth();
		int day = param.getDay();

		LocalDate date = StringOrIntegerToLocalDate.parseDate(year, month, day);

		List<Reservation> reservationList = reservationRepository.findAllByDate(date);
		if (ListUtils.isEmpty(reservationList)) {
			return Collections.emptyList();
		}

		return ReservationDto.fromList(reservationList);
	}

	@Override
	@Transactional
	public void confirm(String memberId, Long reservationId) {
		Optional<Reservation> optionalReservation = reservationRepository.findByMemberUserIdAndId(
			memberId, reservationId);

		if (optionalReservation.isEmpty()) {
			throw new ReservationException(INVALID_RESERVATION);
		}
		Reservation reservation = optionalReservation.get();
		if (reservation.getReservationStatus().equals(COMPLETE)) {
			throw new ReservationException(
				ALREADY_COMPLETED_RESERVATION,
				reservation.getMemberUserId(), reservation.getId());
		}

		if (reservation.getReservationStatus().equals(CANCEL)) {
			throw new ReservationException(
				ALREADY_CANCELED_RESERVATION,
				reservation.getMemberUserId(), reservation.getId());
		}

		reservation.setReservationStatus(COMPLETE);
	}

	@Override
	@Transactional
	public void cancel(String memberId, Long reservationId, LoginType cancelByWho) {
		Optional<Reservation> optionalReservation = reservationRepository.findByMemberUserIdAndId(
			memberId, reservationId);
		if (optionalReservation.isEmpty()) {
			throw new ReservationException(INVALID_RESERVATION);
		}
		Reservation reservation = optionalReservation.get();

		if (reservation.getReservationStatus().equals(CANCEL)) {
			throw new ReservationException(ALREADY_CANCELED_RESERVATION,
				cancelByWho,
				reservation.getMemberUserId(), reservation.getId());
		}
		reservation.cancelReservation();
	}

	@Override
	@Transactional
	public void trainingEnd(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationException(
				INVALID_RESERVATION));

		if (reservation.getReservationStatus().equals(END)) {
			throw new ReservationException(ALREADY_END_RESERVATION,
				reservation.getMemberUserId(), reservationId);
		}

		if (!reservation.getReservationStatus().equals(COMPLETE)) {
			throw new ReservationException(ONLY_COMPLETE_STATUS_CAN_BE_END,
				reservation.getMemberUserId(), reservationId);
		}

		if (!reservation.getDate().isBefore(LocalDate.now())) {
			throw new ReservationException(CAN_NOT_COMPLETE_BEFORE_RESERVATION_DATE,
				reservation.getMemberUserId(), reservationId);
		}

		reservation.setReservationStatus(END);
	}

	@Override
	public boolean hasReservation(Long reservationId, String userId) {
		return reservationRepository.existsByMemberUserIdAndId(userId, reservationId);
	}
}
