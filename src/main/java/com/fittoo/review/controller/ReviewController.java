package com.fittoo.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/review")
public class ReviewController {

	@PostMapping
	public String addReview(Long reservationId, RedirectAttributes attributes) {
		attributes.addAttribute("reservationId", reservationId);
		return "redirect:/review/reviewAddForm";
	}

	@GetMapping("/reviewAddForm")
	public String reviewAddForm(@RequestParam(required = false) Long reservationId) {

		return null;
	}

}
