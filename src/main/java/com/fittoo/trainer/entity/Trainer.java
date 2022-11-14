package com.fittoo.trainer.entity;

import static com.fittoo.common.message.ScheduleErrorMessage.INVALID_DATE;

import com.fittoo.common.entity.UserBaseEntity;
import com.fittoo.exception.ScheduleException;
import com.fittoo.member.model.LoginType;
import com.fittoo.reservation.entity.Reservation;
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Setter
public class Trainer extends UserBaseEntity {

	@Id
	@GeneratedValue
	private Long id;
	private long price;
	private String awards;
	private String introduce;
	private String profilePictureNewName;
	private String profilePictureOriName;
	private int totalScore;
	private int avgScore;
	private int evaluatedNum;

	@OneToMany(mappedBy = "trainer")
	@Builder.Default
	private List<Schedule> scheduleList = new ArrayList<>();

	@OneToMany(mappedBy = "trainer")
	@Builder.Default
	private List<Reservation> reservationList = new ArrayList<>();

	@OneToMany(mappedBy = "trainer")
	@Builder.Default
	private List<Review> reviewList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exercise_type")
	private ExerciseType exerciseType;


	public void addReview(Review review) {
		updateScore(review);
		this.reviewList.add(review);
	}

	private void updateScore(Review review) {
		this.evaluatedNum++;
		this.totalScore += review.getScore();
		this.avgScore = this.totalScore / this.evaluatedNum;
	}

	public void deleteScore(Review review) {
		this.evaluatedNum--;
		this.totalScore -= review.getScore();
		if (evaluatedNum != 0) {
			this.avgScore = totalScore / this.evaluatedNum;
		} else {
			this.avgScore = 0;
		}
	}

	public static Trainer of(TrainerInput trainerInput, String[] fileNames) {
		return Trainer.builder()
			.userId(trainerInput.getUserId())
			.password(trainerInput.getPassword())
			.phoneNumber(trainerInput.getPhoneNumber())
			.loginType(LoginType.TRAINER)
			.awards(trainerInput.getAwards())
			.price(trainerInput.getPrice())
			.exercisePeriod(trainerInput.getExercisePeriod())
			.gender(setGender(trainerInput.getGender()))
			.introduce(trainerInput.getIntroduce())
			.profilePictureOriName(fileNames[0])
			.profilePictureNewName(fileNames[1])
			.userName(trainerInput.getUserName())
			.addr(trainerInput.getAddr())
			.addrDetail(trainerInput.getAddrDetail())
			.zipcode(trainerInput.getZipcode())
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
		setPhoneNumber(input.getPhoneNumber());
		setAwards(input.getAwards());
		setPrice(input.getPrice());
		setGender(setGender(input.getGender()));
		setIntroduce(input.getIntroduce());
		setUserName(input.getUserName());
		setAddr(input.getAddr());
		setAddrDetail(input.getAddrDetail());
		setZipcode(input.getZipcode());
		setAwards(input.getAwards());
		setLoginType(LoginType.TRAINER);
		return this;
	}

	public void updateProfilePicture(String[] filenames) {
		this.profilePictureNewName = filenames[1];
		this.profilePictureOriName = filenames[0];
	}

	public List<Schedule> setSchedule(ScheduleInput input, String trainerId) {
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
				newScheduleList.add(createSchedule(startCalendar, input, trainerId));
			} else {
				while (startCalendar.compareTo(endCalendar) != 0) {
					newScheduleList.add(createSchedule(startCalendar, input, trainerId));
					startCalendar.add(Calendar.DATE, 1);
				}
				newScheduleList.add(createSchedule(startCalendar, input, trainerId));
			}
			return newScheduleList;
		} catch (ParseException e) {
			throw new ScheduleException(INVALID_DATE.message(), e);
		}
	}

	private Schedule createSchedule(Calendar calendar, ScheduleInput input, String trainerId)
		throws ParseException {
		Schedule schedule = new Schedule(trainerId, input.getComment(), input.getPersonnel(),
			this.getExerciseType().getId(), this, calendar, input.getStartTime(),
			input.getEndTime(), input.getPrice());

		this.scheduleList.add(schedule);
		return schedule;
	}

	public void addReservation(Reservation reservation) {
		this.reservationList.add(reservation);
		reservation.setTrainer(this);
	}
}
