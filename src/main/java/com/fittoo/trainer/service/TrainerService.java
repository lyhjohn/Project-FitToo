package com.fittoo.trainer.service;

import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import java.text.ParseException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface TrainerService {

	void trainerRegister(TrainerInput trainerInput);

	TrainerDto findTrainer(String userId);

	List<ScheduleDto> showSchedule(String userId);

	TrainerDto update(UpdateInput input);

	TrainerDto updateProfilePicture(MultipartFile file, String userId);

	List<TrainerDto> findTrainersByClickPage(int curPage);

	void createSchedule(String userId, ScheduleInput input) throws ParseException;

	Long getTotalCountTrainerList();
}
