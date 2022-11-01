package com.fittoo.trainer.service.impl;

import static com.fittoo.common.message.FileErrorMessage.INVALID_FILE;
import static com.fittoo.common.message.FileErrorMessage.INVALID_PROFILE_PICTURE;
import static com.fittoo.common.message.FindErrorMessage.NOT_FOUND_TRAINER;
import static com.fittoo.common.message.RegisterErrorMessage.ALREADY_EXIST_USERID;
import static com.fittoo.common.message.RegisterErrorMessage.Pwd_And_RePwd_Not_Equal;
import static com.fittoo.common.message.ScheduleErrorMessage.INVALID_DATE;
import static com.fittoo.common.message.ScheduleErrorMessage.START_DAY_BIGGER_THAN_END_DAY;

import com.fittoo.exception.FileException;
import com.fittoo.exception.RegisterException;
import com.fittoo.exception.ScheduleException;
import com.fittoo.exception.UserIdAlreadyExist;
import com.fittoo.exception.UserNotFoundException;
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
import com.fittoo.utills.FileStore;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

	private final TrainerRepository trainerRepository;
	private final ScheduleRepository scheduleRepository;
	private final ExerciseTypeRepository exerciseTypeRepository;


	@Override
	@Transactional
	public void trainerRegister(TrainerInput input) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(
			input.getUserId());

		if (optionalTrainer.isPresent()) {
			throw new RegisterException(ALREADY_EXIST_USERID.message(), new UserIdAlreadyExist());
		}

		if (!input.getPassword().equals(input.getRePassword())) {
			input.setLoginType("trainer");
			throw new RegisterException(Pwd_And_RePwd_Not_Equal.message(), input);
		}

		String[] fileNames;
		try {
			fileNames = new FileStore().storeFile(input.getProfilePicture(), "trainer");
		} catch (IOException e) {
			throw new RegisterException(INVALID_FILE.message(), new FileException());
		}

		String encPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());
		input.setPassword(encPassword);
		Trainer trainer = Trainer.of(input, fileNames);

		Optional<ExerciseType> optionalExerciseType = exerciseTypeRepository.findById(
			input.getExerciseType());

		trainerRepository.save(trainer);

		if (optionalExerciseType.isEmpty()) {
			ExerciseType exerciseType = exerciseTypeRepository.save(
				new ExerciseType(input.getExerciseType()));
			exerciseType.addTrainer(trainer);
			exerciseTypeRepository.save(exerciseType);
		} else {
			ExerciseType exerciseType = optionalExerciseType.get();
			exerciseType.addTrainer(trainer);
			exerciseTypeRepository.save(exerciseType);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TrainerDto findTrainer(String userId) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);



		return optionalTrainer.map(TrainerDto::of).orElseThrow(()
			-> new UserNotFoundException(NOT_FOUND_TRAINER.message()));
	}

	@Override
	@Transactional
	public TrainerDto update(UpdateInput input) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(input.getUserId());

		return optionalTrainer.map(x -> TrainerDto.of(x.update(input)))
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_TRAINER.message()));
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
			throw new FileException(INVALID_PROFILE_PICTURE.message());
		}
		trainer.updateProfilePicture(fileNames);

		return TrainerDto.of(trainer);
	}

	@Override
	@Transactional
	public List<TrainerDto> findAll() {
		List<Trainer> trainerList = trainerRepository.findAll();

		return TrainerDto.of(trainerList);
	}

	@Override
	@Transactional
	public Optional<List<ScheduleDto>> showSchedule(String userId) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);
		if (optionalTrainer.isEmpty()) {
			return Optional.empty();
		}
		Trainer trainer = optionalTrainer.get();
		return Optional.ofNullable(ScheduleDto.of(trainer.getScheduleList()));
	}

	@Override
	@Transactional
	public void createSchedule(String userId, ScheduleInput input) {
		if (!isStartDateLowerThanEndDate(input)) {
			throw new ScheduleException(START_DAY_BIGGER_THAN_END_DAY.message());
		}

		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);
		if (optionalTrainer.isEmpty()) {
			throw new UserNotFoundException(NOT_FOUND_TRAINER.message());
		}
		Trainer trainer = optionalTrainer.get();

		List<Schedule> scheduleList = trainer.setSchedule(input);
		scheduleRepository.saveAll(scheduleList);
	}

	private static boolean isStartDateLowerThanEndDate(ScheduleInput input) {
		try {
			return
				LocalDate.parse(input.getStartDate()).getYear()
					<= LocalDate.parse(input.getEndDate()).getYear()
					&& LocalDate.parse(input.getStartDate()).getMonthValue()
					<= LocalDate.parse(input.getEndDate()).getMonthValue()
					&& LocalDate.parse(input.getStartDate()).getDayOfMonth()
					<= LocalDate.parse(input.getEndDate()).getDayOfMonth();
		} catch (DateTimeParseException e) {
			throw new ScheduleException(INVALID_DATE.message(), e);
		}
	}
}
