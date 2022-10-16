package com.fittoo.trainer.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import org.springframework.web.multipart.MultipartFile;

public interface TrainerService {
    ServiceResult trainerRegister(TrainerInput trainerInput);
    TrainerDto lookUpProfile(String userId);
}
