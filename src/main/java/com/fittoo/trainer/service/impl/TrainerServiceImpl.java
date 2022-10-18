package com.fittoo.trainer.service.impl;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.utills.FileStore;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.repository.TrainerRepository;
import com.fittoo.trainer.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;


    @Override
    @Transactional
    public ServiceResult trainerRegister(TrainerInput trainerInput) {
        Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(trainerInput.getUserId());
        if (optionalTrainer.isPresent()) {
            return new ServiceResult(false, ErrorMessage.ALREADY_EXIST_USERID);
        }

        String[] fileNames;
        try {
            fileNames = new FileStore().storeFile(trainerInput.getProfilePicture(), "trainer");
        } catch (IOException e) {
            return new ServiceResult(false, ErrorMessage.INVALID_FILE);
        }

        String encPassword = BCrypt.hashpw(trainerInput.getPassword(), BCrypt.gensalt());
        Trainer trainer = Trainer.of(trainerInput, fileNames, encPassword);
        trainerRepository.save(trainer);

        return new ServiceResult();
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerDto findTrainer(String userId) {
        Optional<Trainer> optionalTrainer = trainerRepository.findByUserId(userId);
        if (optionalTrainer.isEmpty()) {
            return null;
        }
        if (!optionalTrainer.get().getUserId().equals(userId)) {
            return null;
        }

        return optionalTrainer.map(TrainerDto::of).orElse(null);
    }

}
