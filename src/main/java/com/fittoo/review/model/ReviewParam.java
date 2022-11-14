package com.fittoo.review.model;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReviewParam {

	@NotNull
	private Long reservationId;
	private String comment;
	private int score;
	private String trainerId;
}
