package com.fittoo.reservation.controller;

import com.fittoo.member.model.ReservationParam;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.entity.Schedule;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.service.TrainerService;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public String reservation(ReservationParam param, Principal principal, Model model) {
		ScheduleDto schedule = reservationService.getSchedule(
			getDate(param.getYear(), param.getCurrentMonth(), param.getDay()),
			param.getTrainerId());

		model.addAttribute("schedule", schedule);
		return "/reservation/reservationForm";
	}

	@GetMapping("/empty")
	public String emptySchedule(@RequestParam(required = false) String errorMessage, Model model) {
		if (StringUtils.hasText(errorMessage)) {
			model.addAttribute("errorMessage", errorMessage);
		}
		return "/reservation/emptySchedule";
	}

	public LocalDate getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
	}
}
