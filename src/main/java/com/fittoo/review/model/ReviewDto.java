package com.fittoo.review.model;

import com.fittoo.member.entity.Member;
import com.fittoo.review.entity.Review;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.model.TrainerDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewDto {

	private String comment;
	private int score;
	private Long reservationId;

	private String trainerId;

	@Builder
	public ReviewDto(String comment, int score, Long reservationId, String trainerId) {
		this.comment = comment;
		this.score = score;
		this.reservationId = reservationId;
		this.trainerId = trainerId;
	}

	public static ReviewDto of(Review review) {
		return ReviewDto.builder()
			.trainerId(review.getTrainer().getUserId())
			.comment(review.getComment())
			.score(review.getScore())
			.reservationId(review.getReservationId())
			.build();
	}

	public static List<ReviewDto> ofList(List<Review> reviewList) {
		List<ReviewDto> list = new ArrayList<>();
		reviewList.forEach(x -> {
				list.add(ReviewDto.of(x));
			}
		);
		return list;
	}
}
