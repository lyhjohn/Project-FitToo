package com.fittoo.trainer.service.impl;

import static com.fittoo.common.message.CommonErrorMessage.NOT_FOUND_USER;
import static com.fittoo.common.message.FileErrorMessage.INVALID_FILE;
import static com.fittoo.common.message.FileErrorMessage.INVALID_PROFILE_PICTURE;
import static com.fittoo.common.message.FindErrorMessage.NOT_FOUND_TRAINER;
import static com.fittoo.common.message.RegisterErrorMessage.ALREADY_EXIST_USERID;
import static com.fittoo.common.message.RegisterErrorMessage.Pwd_And_RePwd_Not_Equal;
import static com.fittoo.common.message.ScheduleErrorMessage.CAN_RESERVE_AFTER_NOW;
import static com.fittoo.common.message.ScheduleErrorMessage.CONTAINS_REGISTERED_DATE;
import static com.fittoo.common.message.ScheduleErrorMessage.EMPTY_SCHEDULE;
import static com.fittoo.common.message.ScheduleErrorMessage.ONLY_CANCEL_STATUS_CAN_DELETE;
import static com.fittoo.common.message.ScheduleErrorMessage.START_DAY_BIGGER_THAN_END_DAY;
import static com.fittoo.common.message.ScheduleErrorMessage.START_TIME_BIGGER_THAN_END_TIME;
import static com.fittoo.reservation.constant.ReservationStatus.CANCEL;
import static com.fittoo.trainer.entity.QTrainer.trainer;
import static com.fittoo.utills.CalendarUtil.StringOrIntegerToLocalDate.getStartDate;
import static com.fittoo.utills.CalendarUtil.StringOrIntegerToLocalDate.parseDate;
import static com.fittoo.utills.CalendarUtil.StringToLocalTime.getEndTime;
import static com.fittoo.utills.CalendarUtil.StringToLocalTime.getStartTime;

import com.fittoo.exception.FileException;
import com.fittoo.exception.RegisterException;
import com.fittoo.exception.ScheduleException;
import com.fittoo.exception.UserIdAlreadyExist;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.reservation.entity.Reservation;
import com.fittoo.reservation.repository.ReservationRepository;
import com.fittoo.trainer.entity.ExerciseType;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import com.fittoo.trainer.repository.ExerciseTypeRepository;
import com.fittoo.trainer.repository.ScheduleRepository;
import com.fittoo.trainer.repository.TrainerRepository;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.utills.CalendarUtil.StringOrIntegerToLocalDate;
import com.fittoo.utills.FileStore;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

	private final TrainerRepository trainerRepository;
	private final ScheduleRepository scheduleRepository;
	private final ExerciseTypeRepository exerciseTypeRepository;
	private final JPAQueryFactory queryFactory;

	private final ReservationRepository reservationRepository;
	private final static String TRAINER = "trainer";

	@Override
	@Transactional
	public void trainerRegister(TrainerInput input) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(
			input.getUserId());

		if (optionalTrainer.isPresent()) {
			input.setLoginType(TRAINER);
			throw new RegisterException(ALREADY_EXIST_USERID, input, TRAINER,
				new UserIdAlreadyExist());
		}

		if (!input.getPassword().equals(input.getRepassword())) {
			input.setLoginType(TRAINER);
			throw new RegisterException(Pwd_And_RePwd_Not_Equal.message(), input, TRAINER);
		}

		String[] fileNames;
		try {
			fileNames = new FileStore().storeFile(input.getProfilePicture(), TRAINER);
		} catch (IOException e) {
			throw new FileException(INVALID_FILE);
		}

		String encPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());
		input.setPassword(encPassword);
		Trainer trainer = Trainer.of(input, fileNames);

		Optional<ExerciseType> optionalExerciseType = exerciseTypeRepository.findById(
			input.getExerciseType());

		try {
			trainerRepository.save(trainer);
		} catch (
			DataIntegrityViolationException e) {
			input.setLoginType(TRAINER);
			throw new RegisterException(ALREADY_EXIST_USERID, input, TRAINER, e);
		}

		ExerciseType exerciseType;
		if (optionalExerciseType.isEmpty()) {
			exerciseType = exerciseTypeRepository.save(
				new ExerciseType(input.getExerciseType()));
		} else {
			exerciseType = optionalExerciseType.get();
		}
		exerciseType.addTrainer(trainer);
		exerciseTypeRepository.save(exerciseType);
	}

	@Override
	@Transactional(readOnly = true)
	public TrainerDto findTrainer(String userId) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);

		return optionalTrainer.map(TrainerDto::of).orElseThrow(()
			-> new UserNotFoundException(NOT_FOUND_TRAINER));
	}

	@Override
	@Transactional
	public TrainerDto update(UpdateInput input) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(input.getUserId());

		return optionalTrainer.map(x -> TrainerDto.of(x.update(input)))
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_TRAINER));
	}

	@Override
	@Transactional
	public TrainerDto updateProfilePicture(MultipartFile file, String userId) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);
		if (optionalTrainer.isEmpty()) {
			return null;
		}
		Trainer trainer = optionalTrainer.get();

		String[] fileNames;
		try {
			fileNames = new FileStore().storeFile(file, "trainer");
		} catch (IOException e) {
			throw new FileException(INVALID_PROFILE_PICTURE);
		}
		trainer.updateProfilePicture(fileNames);

		return TrainerDto.of(trainer);
	}

	@Override
	@Transactional
	public List<TrainerDto> findTrainersByClickPage(int page) {
		PageRequest pageRequest = PageRequest.of(page - 1, 5, Direction.ASC, "userName");

		List<Trainer> trainerList = queryFactory
			.selectFrom(trainer)
			.offset(pageRequest.getOffset())
			.limit(pageRequest.getPageSize())
			.fetch();

		return TrainerDto.of(trainerList);
	}

	@Override
	public Long getTotalCountTrainerList() {
		return queryFactory
			.select(Wildcard.count)
			.from(trainer)
			.fetchOne();
	}

	@Override
	@Transactional
	public List<ScheduleDto> showSchedule(String userId) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);
		if (optionalTrainer.isEmpty()) {
			throw new UserNotFoundException(NOT_FOUND_USER);
		}
		Trainer trainer = optionalTrainer.get();
		List<ScheduleDto> scheduleList = ScheduleDto.of(trainer.getScheduleList());
		if (CollectionUtils.isEmpty(scheduleList)) {
			return Collections.emptyList();
		}
		return scheduleList;
	}

	@Override
	@Transactional
	public void createSchedule(String userId, ScheduleInput input) throws ParseException {
		if (!isStartDateIsBeforeEndDate(input)) {
			throw new ScheduleException(START_DAY_BIGGER_THAN_END_DAY);
		}

		if (!isStartTimeIsBeforeEndTime(input)) {
			throw new ScheduleException(START_TIME_BIGGER_THAN_END_TIME);
		}

		if (isBeforeNow(input)) {
			throw new ScheduleException(CAN_RESERVE_AFTER_NOW);
		}

		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);
		if (optionalTrainer.isEmpty()) {
			throw new UserNotFoundException(NOT_FOUND_TRAINER);
		}

		Trainer trainer = optionalTrainer.get();
		LocalDate startDate = getStartDate(input.getStartDate());
		LocalDate endDate = StringOrIntegerToLocalDate.getEndDate(input.getEndDate());

		Optional<List<Schedule>> optionalScheduleList = scheduleRepository.findAllByTrainerUserIdAndDateBetween(
			trainer.getUserId(), startDate, endDate);

		if (optionalScheduleList.isPresent()) {
			if (!CollectionUtils.isEmpty(optionalScheduleList.get())) {
				throw new ScheduleException(CONTAINS_REGISTERED_DATE);
			}
		}

		List<Schedule> scheduleList = trainer.setSchedule(input, trainer.getUserId());
		scheduleRepository.saveAll(scheduleList);
	}

	private static boolean isStartDateIsBeforeEndDate(ScheduleInput input) throws ParseException {
		if (input.getStartDate().equals(input.getEndDate())) {
			return true;
		}

		return getStartDate(input.getStartDate())
			.isBefore(StringOrIntegerToLocalDate.getEndDate(input.getEndDate()));
	}

	private static boolean isStartTimeIsBeforeEndTime(ScheduleInput input) throws ParseException {
		return getStartTime(input.getStartTime()).isBefore(getEndTime(input.getEndTime()));
	}

	private static boolean isBeforeNow(ScheduleInput input) throws ParseException {
		return getStartDate(input.getStartDate()).isBefore(LocalDate.now()) ||
			(getStartDate(input.getStartDate()).equals(LocalDate.now()) &&
				getStartTime(input.getStartTime()).isBefore(LocalTime.now())
			);
	}

	@Override
	@Transactional
	public void completeWithdraw(String userId) {
		trainerRepository.deleteByUserId(userId);
	}

	@Override
	@Transactional
	public void deleteSchedule(String date, String trainerId) throws ParseException {

		Schedule schedule = scheduleRepository.findByDateAndTrainerUserId(parseDate(date),
				trainerId)
			.orElseThrow(() -> new ScheduleException(EMPTY_SCHEDULE));

		List<Reservation> reservationList = schedule.getReservationList();
		for (Reservation reservation : reservationList) {
			if (!reservation.getReservationStatus().equals(CANCEL)) {
				throw new ScheduleException(ONLY_CANCEL_STATUS_CAN_DELETE);
			}
		}

		reservationRepository.deleteAll(reservationList);
		scheduleRepository.deleteById(schedule.getId());
	}
}
