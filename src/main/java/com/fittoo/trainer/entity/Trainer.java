package com.fittoo.trainer.entity;

import com.fittoo.common.entity.UserBaseEntity;
import com.fittoo.common.message.ErrorMessage;
import com.fittoo.exception.DateParseException;
import com.fittoo.member.model.LoginType;
import com.fittoo.reservation.Reservation;
import com.fittoo.review.entity.Review;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
public class Trainer extends UserBaseEntity {

	@Id
	@GeneratedValue
	private Long id;
	private long price;
	private String awards;
	private String mainPtList;
	private String introduce;
	private String profilePictureNewName;
	private String profilePictureOriName;

	@OneToMany(mappedBy = "trainer")
	@Builder.Default
	private List<Schedule> scheduleList = new ArrayList<>();

	@OneToMany(mappedBy = "trainer")
	@Builder.Default
	private List<Review> reviewList = new ArrayList<>();

	@OneToMany(mappedBy = "trainer")
	@Builder.Default
	private List<Reservation> reservationList = new ArrayList<>();


	public void addReservation(Reservation reservation) {
		this.reservationList.add(reservation);
		reservation.setTrainer(this);
	}

	public void addReview(Review review) {
		this.reviewList.add(review);
		review.setTrainer(this);
	}


	public static String setMainPtList(List<String> ptList) {
		StringBuilder sb = new StringBuilder();
		int length = ptList.size();
		for (String pt : ptList) {
			if (--length == 0) {
				sb.append(pt);
			} else {
				sb.append(pt).append(",");
			}
		}
		return sb.toString();
	}

	public static Trainer of(TrainerInput trainerInput, String[] fileNames) {
		return Trainer.builder()
			.userId(trainerInput.getUserId())
			.password(trainerInput.getPassword())
			.phoneNumber(trainerInput.getPhoneNumber())
			.loginType(LoginType.TRAINER)
			.awards(trainerInput.getAwards())
			.mainPtList(setMainPtList(trainerInput.getMainPtList()))
			.price(trainerInput.getPrice())
			.exercisePeriod(trainerInput.getExercisePeriod())
			.gender(setGender(trainerInput.getGender()))
			.introduce(trainerInput.getIntroduce())
			.profilePictureOriName(fileNames[0])
			.profilePictureNewName(fileNames[1])
			.userName(trainerInput.getUserName())
			.address(trainerInput.getAddress())
			.regDt(LocalDateTime.now())
			.awards(trainerInput.getAwards())
			.build();
	}

	public static Trainer of(TrainerInput trainerInput) {
		return Trainer.builder()
			.userId(trainerInput.getUserId())
			.password(trainerInput.getPassword())
			.phoneNumber(trainerInput.getPhoneNumber())
			.loginType(LoginType.TRAINER)
			.awards(trainerInput.getAwards())
			.mainPtList(setMainPtList(trainerInput.getMainPtList()))
			.price(trainerInput.getPrice())
			.exercisePeriod(trainerInput.getExercisePeriod())
			.gender(setGender(trainerInput.getGender()))
			.introduce(trainerInput.getIntroduce())
			.userName(trainerInput.getUserName())
			.address(trainerInput.getAddress())
			.regDt(LocalDateTime.now())
			.awards(trainerInput.getAwards())
			.build();
	}

	public static String setGender(int num) {
		switch (num) {
			case 1:
				return "남자";
			case 2:
				return "여자";
		}
		return null;
	}

	public Trainer update(UpdateInput input) {
		this.setPhoneNumber(input.getPhoneNumber());
		this.setAwards(input.getAwards());
		this.setPrice(input.getPrice());
		this.setGender(setGender(input.getGender()));
		this.setIntroduce(input.getIntroduce());
		this.setUserName(input.getUserName());
		this.setAddress(input.getAddress());
		this.setUdtDt(LocalDateTime.now());
		this.setAwards(input.getAwards());
		this.setLoginType(LoginType.TRAINER);
		return this;
	}

	public void updateProfilePicture(String[] filenames) {
		this.profilePictureNewName = filenames[1];
		this.profilePictureOriName = filenames[0];
	}

	public List<Schedule> setSchedule(ScheduleInput input) {
		List<Schedule> newScheduleList = new ArrayList<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date parseStartDate = df.parse(input.getStartDate());
			Date parseEndDate = df.parse(input.getEndDate());

			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();

			startCalendar.setTime(parseStartDate);
			endCalendar.setTime(parseEndDate);

			if (startCalendar == endCalendar) {
				newScheduleList.add(createSchedule(startCalendar, input.getComment()));
			} else {
				while (startCalendar.compareTo(endCalendar) != 0) {
					newScheduleList.add(createSchedule(startCalendar, input.getComment()));
					startCalendar.add(Calendar.DATE, 1);
				}
				newScheduleList.add(createSchedule(startCalendar, input.getComment()));
			}
			return newScheduleList;
		} catch (ParseException e) {
			throw new DateParseException(ErrorMessage.INVALID_DATE.message(), e);
		}
	}

	private Schedule createSchedule(Calendar calendar, String comment) {
		Schedule schedule = new Schedule(calendar);
		schedule.setComment(comment);
		this.scheduleList.add(schedule);
		schedule.setTrainer(this);
		return schedule;
	}
}
