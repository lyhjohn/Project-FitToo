package com.fittoo.trainer.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface TrainerService {

	ServiceResult trainerRegister(TrainerInput trainerInput);

	TrainerDto findTrainer(String userId);

	ScheduleDto showSchedule(String userId);

	TrainerDto update(UpdateInput input);

	TrainerDto updateProfilePicture(MultipartFile file, String userId);

	List<TrainerDto> findAll();

	ServiceResult createSchedule(String userId, ScheduleInput input);
}
