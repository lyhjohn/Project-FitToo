package com.fittoo.reservation.controller;

import com.fittoo.member.model.ReservationParam;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.entity.Schedule;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
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
	public String reservation(ReservationParam param, Principal principal, Model model) {


		model.addAttribute("trainerId", param.getTrainerId());
		return "/reservation/reservationForm";
	}


	public LocalDate getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
	}
}
