package com.fittoo.review.service;

import static com.fittoo.common.message.CommonErrorMessage.NOT_FOUND_USER;
import static com.fittoo.common.message.ReviewErrorMessage.ALREADY_EXIST_REVIEW;
import static com.fittoo.common.message.ReviewErrorMessage.NOT_FOUND_REVIEW;

import com.fittoo.exception.ReviewException;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.member.entity.Member;
import com.fittoo.member.repository.MemberRepository;
import com.fittoo.review.entity.QReview;
import com.fittoo.review.entity.Review;
import com.fittoo.review.model.ReviewDto;
import com.fittoo.review.model.ReviewParam;
import com.fittoo.review.repository.ReviewRepository;
import com.fittoo.trainer.entity.Trainer;
import com.fittoo.trainer.repository.TrainerRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final TrainerRepository trainerRepository;
	private final JPAQueryFactory queryFactory;

	@Transactional
	public void addReview(ReviewParam param, String memberId) {
		boolean result = reviewRepository.existsByReservationId(param.getReservationId());
		if (result) {
			throw new ReviewException(ALREADY_EXIST_REVIEW);
		}

		Member member = memberRepository.findByUserId(memberId)
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));

		Trainer trainer = trainerRepository.findByUserId(param.getTrainerId())
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));
		Review review = new Review(param.getComment(), param.getScore(), param.getReservationId());

		review.addMember(member);
		review.addTrainer(trainer);
		reviewRepository.save(review);
	}

	@Transactional
	public void deleteReview(ReviewParam param, String userId) {
		Review review = queryFactory.selectFrom(QReview.review)
			.where(QReview.review.reservationId.eq(param.getReservationId())
				.and(QReview.review.member.userId.eq(userId)))
			.fetchOne();

		if (review == null) {
			throw new ReviewException(NOT_FOUND_REVIEW);
		}
		trainerRepository.findByUserId(param.getTrainerId()).ifPresent(x -> x.deleteScore(review));
		reviewRepository.delete(review);
	}

	public List<ReviewDto> reviewListWrittenByUser(String userId) {
		Member member = memberRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));
		List<Review> reviewList = member.getReviewList();
		if (CollectionUtils.isEmpty(reviewList)) {
			return Collections.emptyList();
		}
		return ReviewDto.ofList(reviewList);
	}

	public Map<List<ReviewDto>, Integer> reviewListWrittenToTrainer(String trainerId) {
		Trainer trainer = trainerRepository.findByUserId(trainerId)
			.orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));
		List<Review> reviewList = trainer.getReviewList();
		if (CollectionUtils.isEmpty(reviewList)) {
			return Collections.emptyMap();
		}
		Map<List<ReviewDto>, Integer> reviewListAndEvaluatedNum = new HashMap<>();
		List<ReviewDto> reviews = ReviewDto.ofList(reviewList);
		reviewListAndEvaluatedNum.put(reviews, trainer.getEvaluatedNum());
		return reviewListAndEvaluatedNum;
	}
}
