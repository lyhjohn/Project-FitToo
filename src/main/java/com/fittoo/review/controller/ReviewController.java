package com.fittoo.review.controller;

import static com.fittoo.common.message.ReservationErrorMessage.INVALID_RESERVATION;

import com.fittoo.exception.ReservationException;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.review.model.ReviewDto;
import com.fittoo.review.model.ReviewParam;
import com.fittoo.review.service.ReviewService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private final ReservationService reservationService;
	private final ReviewService reviewService;

	@PostMapping("/add")
	public String addReview(Long reservationId, String trainerId, RedirectAttributes attributes) {
		attributes.addAttribute("trainerId", trainerId);
		attributes.addAttribute("reservationId", reservationId);
		return "redirect:/review/reviewAddForm";
	}

	@GetMapping("/reviewAddForm")
	public String reviewAddForm(@RequestParam Long reservationId, @RequestParam String trainerId,
		Principal principal, Model model) {
		boolean result = reservationService.hasReservation(reservationId, principal.getName());
		if (!result) {
			throw new ReservationException(INVALID_RESERVATION.message());
		}

		model.addAttribute("trainerId", trainerId);
		model.addAttribute("reservationId", reservationId);
		return "review/reviewAddForm";
	}

	@PostMapping("/submit")
	public String submitReview(ReviewParam param, Principal principal) {
		reviewService.addReview(param, principal.getName());

		return "redirect:/reservation/view";
	}

	@PostMapping("/viewList")
	public String reviewWrittenByMe(Principal principal, Model model) {
		List<ReviewDto> reviewList = reviewService.reviewListWrittenByUser(principal.getName());
		model.addAttribute("reviewList", reviewList);
		return "/member/reviewList";
	}

	@PostMapping("/delete")
	public String deleteReview(ReviewParam param, Principal principal) {
		reviewService.deleteReview(param, principal.getName());
		return "redirect:/member/profile";
	}

	@GetMapping("/viewList")
	public String reviewWrittenToTrainer(@RequestParam String trainerId, Model model) {
		Map<List<ReviewDto>, Integer> reviewList = reviewService.reviewListWrittenToTrainer(trainerId);
		for (List<ReviewDto> review : reviewList.keySet()) {
			model.addAttribute("evaluatedNum", reviewList.get(review));
			model.addAttribute("reviewList", review);
		}
		return "/trainer/reviewList";
	}
}
