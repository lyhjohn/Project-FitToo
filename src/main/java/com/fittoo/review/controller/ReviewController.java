package com.fittoo.review.controller;

import com.fittoo.reservation.service.ReservationService;
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

	@PostMapping
	public String addReview(Long reservationId, RedirectAttributes attributes) {
		attributes.addAttribute("reservationId", reservationId);
		return "redirect:/review/reviewAddForm";
	}

	@GetMapping("/reviewAddForm")
	public String reviewAddForm(@RequestParam(required = false) Long reservationId, Model model) {
		if (reservationId != null) {
			model.addAttribute("reservationId", reservationId);
		}
		return "review/reviewAddForm";
	}
}
