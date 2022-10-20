package com.fittoo.trainer.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrainerService {
    ServiceResult trainerRegister(TrainerInput trainerInput);
    TrainerDto findTrainer(String userId);

    TrainerDto update(UpdateInput input);

    TrainerDto updateProfilePicture(MultipartFile file, String userId);

    List<TrainerDto> findAll();
}
