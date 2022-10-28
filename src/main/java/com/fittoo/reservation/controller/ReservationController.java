package com.fittoo.reservation.controller;

import com.fittoo.member.model.ReservationParam;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.entity.Schedule;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

	private final ReservationService reservationService;
	@PostMapping
	public String reservation(ReservationParam param, Principal principal, Model model,
		String date) {


		model.addAttribute("trainerId", param.getTrainerId());
		model.addAttribute("date", date);
		return "/reservation/reservationForm";
	}
}
