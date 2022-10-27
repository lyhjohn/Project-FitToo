package com.fittoo.reservation.controller;

import com.fittoo.member.model.ReservationParam;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {

	@PostMapping("/reservation")
	public String reservation(ReservationParam param, Principal principal, Model model,
		String date) {

		model.addAttribute("trainerId", param.getTrainerId());
		model.addAttribute("date", date);
		return "/reservation/reservationForm";
	}
}
