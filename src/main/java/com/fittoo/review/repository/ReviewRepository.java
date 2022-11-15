package com.fittoo.review.repository;

import com.fittoo.review.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	boolean existsByReservationId(Long reviewId);
}
