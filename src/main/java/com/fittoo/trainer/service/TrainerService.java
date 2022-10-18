package com.fittoo.trainer.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;

public interface TrainerService {
    ServiceResult trainerRegister(TrainerInput trainerInput);
    TrainerDto findTrainer(String userId);

}
