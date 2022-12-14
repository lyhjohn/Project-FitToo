package com.fittoo.member.entity;

import com.fittoo.common.entity.UserBaseEntity;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.LoginType;
import com.fittoo.reservation.entity.Reservation;
import com.fittoo.review.entity.Review;
import com.fittoo.trainer.model.UpdateInput;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Member extends UserBaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	private long point;
	@OneToMany(mappedBy = "member")
	@Builder.Default
	private List<Reservation> reservationList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	@Builder.Default
	private List<Review> reviewList = new ArrayList<>();

	public void addReservation(Reservation reservation) {
		this.reservationList.add(reservation);
		reservation.setMember(this);
	}

	public void addReview(Review review) {
		this.reviewList.add(review);
	}


	public static Member of(MemberInput memberInput, String encPassword) {
		return Member.builder()
			.userId(memberInput.getUserId())
			.password(encPassword)
			.gender(setGender(memberInput.getGender()))
			.loginType(LoginType.NORMAL)
			.phoneNumber(memberInput.getPhoneNumber())
			.exercisePeriod(memberInput.getExercisePeriod())
			.userName(memberInput.getUserName())
			.build();
	}

	public void update(UpdateInput input) {
		setUserId(input.getUserId());
		setPhoneNumber(input.getPhoneNumber());
		setGender(setGender(input.getGender()));
		setUserName(input.getUserName());
		setExercisePeriod(input.getExercisePeriod());
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
}
