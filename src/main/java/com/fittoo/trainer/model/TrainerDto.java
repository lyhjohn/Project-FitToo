package com.fittoo.trainer.model;

import com.fittoo.common.model.BaseDto;
import com.fittoo.member.model.LoginType;
import com.fittoo.trainer.entity.Trainer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TrainerDto extends BaseDto {

    private Long id;
    private long price;
    private String awards;
    private String mainPtList;
    private String introduce;
    private String profilePictureNewName;
    private String profilePictureOriName;
    private LocalDateTime regDt;



    public static TrainerDto of(Trainer trainer) {
        return TrainerDto.builder()
                .userId(trainer.getUserId())
                .phoneNumber(trainer.getPhoneNumber())
                .loginType(LoginType.TRAINER)
                .awards(trainer.getAwards())
                .mainPtList(trainer.getMainPtList())
                .price(trainer.getPrice())
                .exercisePeriod(trainer.getExercisePeriod())
                .gender(trainer.getGender())
                .introduce(trainer.getIntroduce())
                .profilePictureOriName(trainer.getProfilePictureOriName())
                .profilePictureNewName(trainer.getProfilePictureNewName())
                .userName(trainer.getUserName())
                .region(trainer.getRegion())
                .regDt(trainer.getRegDt())
                .build();
    }

    public String getRegDt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

        return regDt != null ? this.regDt.format(formatter) : "";
    }
}
