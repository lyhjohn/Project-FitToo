package com.fittoo.trainer.model;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.BaseDto;
import com.fittoo.member.model.LoginType;
import com.fittoo.trainer.entity.Trainer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                .power(trainer.getPower())
                .build();
    }

    public String getRegDt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

        return regDt != null ? this.regDt.format(formatter) : "";
    }


    public static void whatIsGender(Object gender, Model model) {
        if (gender != null) {
            if (gender instanceof String) {
                if (gender.equals("남자")) {
                    model.addAttribute("isMan", true);
                } else {
                    model.addAttribute("isGirl", true);
                }
            }

            if (gender instanceof Integer) {
                if ((int) gender == 1) {
                    model.addAttribute("isMan", true);
                } else {
                    model.addAttribute("isGirl", true);
                }
            }
        }
    }

    public static boolean checkLoginType(TrainerDto trainer, HttpServletRequest request, Model model) {
        if (trainer == null) {
            model.addAttribute("errorMessage", ErrorMessage.INVALID_ID_OR_PWD.description());
            HttpSession session = request.getSession();
            session.invalidate();
            return false;
        }
        model.addAttribute("member", trainer);
        return true;
    }
}
