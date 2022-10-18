package com.fittoo.trainer.service;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;

public interface TrainerService {
    ServiceResult trainerRegister(TrainerInput trainerInput);
    TrainerDto findTrainer(String userId);

    TrainerDto update(UpdateInput input);
}
