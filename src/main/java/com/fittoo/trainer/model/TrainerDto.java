package com.fittoo.trainer.model;

import com.fittoo.common.model.UserBaseDto;
import com.fittoo.member.model.LoginType;
import com.fittoo.reservation.dto.ReservationDto;
import com.fittoo.trainer.entity.Trainer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.ui.Model;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class TrainerDto extends UserBaseDto {

	private long price;
	private String awards;
	private String mainPtList;
	private String introduce;
	private String profilePictureNewName;
	private String profilePictureOriName;
	private LocalDateTime regDt;

	@Builder.Default
	private List<ReservationDto> reservation = new ArrayList<>();

	private List<ScheduleDto> scheduleList;


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
//			.reservation(trainer.getReservationList().stream().map(ReservationDto::of)
//				.collect(Collectors.toList()))
			.userName(trainer.getUserName())
			.address(trainer.getAddress())
			.regDt(trainer.getRegDt())
			.scheduleList(ScheduleDto.of(trainer.getScheduleList()))
			.build();
	}

	public static List<TrainerDto> of(List<Trainer> trainerList) {
		List<TrainerDto> list = new ArrayList<>();
		trainerList.forEach(trainer -> list.add(TrainerDto.of(trainer)));
		return list;
	}

	public String getRegDt() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

		return this.regDt != null ? this.regDt.format(formatter) : "";
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
}
