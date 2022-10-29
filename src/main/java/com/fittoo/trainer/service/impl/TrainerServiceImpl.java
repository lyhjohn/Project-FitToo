package com.fittoo.trainer.service.impl;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.exception.DateParseException;
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
	public ServiceResult trainerRegister(TrainerInput input) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(
			input.getUserId());

		if (optionalTrainer.isPresent()) {
			return new ServiceResult(false, ErrorMessage.ALREADY_EXIST_USERID);
		}

		String[] fileNames;
		try {
			fileNames = new FileStore().storeFile(input.getProfilePicture(), "trainer");
		} catch (IOException e) {
			return new ServiceResult(false, ErrorMessage.INVALID_FILE);
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


		return new ServiceResult();
	}

	@Override
	@Transactional(readOnly = true)
	public TrainerDto findTrainer(String userId) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);

		return optionalTrainer.map(TrainerDto::of).orElseThrow(()
			-> new UserNotFoundException(ErrorMessage.NOT_FOUND_TRAINER.message(),
			new RuntimeException()));
	}

	@Override
	@Transactional
	public TrainerDto update(UpdateInput input) {
		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(input.getUserId());

		return optionalTrainer.map(x -> TrainerDto.of(x.update(input)))
			.orElseThrow(() -> new UserNotFoundException(ErrorMessage.NOT_FOUND_TRAINER.message(),
				new RuntimeException()));
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
			return null;
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
	public ServiceResult createSchedule(String userId, ScheduleInput input) {
		if (!isStartDateLowerThanEndDate(input)) {
			return new ServiceResult(false, ErrorMessage.START_DAY_BIGGER_THAN_END_DAY);
		}

		Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);
		if (optionalTrainer.isEmpty()) {
			return new ServiceResult(false, ErrorMessage.NOT_FOUND_TRAINER);
		}
		Trainer trainer = optionalTrainer.get();
		try {
			List<Schedule> scheduleList = trainer.setSchedule(input);
			scheduleRepository.saveAll(scheduleList);

		} catch (DateParseException e) {
			return new ServiceResult(false, ErrorMessage.INVALID_DATE);
		}

		return new ServiceResult();
	}

	private static boolean isStartDateLowerThanEndDate(ScheduleInput input) {
		return
			LocalDate.parse(input.getStartDate()).getYear() <= LocalDate.parse(input.getEndDate())
				.getYear() &&
				LocalDate.parse(input.getStartDate()).getMonthValue() <= LocalDate.parse(
					input.getEndDate()).getMonthValue() &&
				LocalDate.parse(input.getStartDate()).getDayOfMonth() <= LocalDate.parse(
					input.getEndDate()).getDayOfMonth();
	}
}
