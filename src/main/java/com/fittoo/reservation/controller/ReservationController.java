package com.fittoo.reservation.controller;

import static com.fittoo.common.message.ReservationErrorMessage.EMPTY_SCHEDULE;
import static com.fittoo.utills.CalendarUtil.StringOrIntegerToLocalDate.parseDate;

import com.fittoo.common.message.ReservationErrorMessage;
import com.fittoo.exception.ReservationException;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.reservation.model.SearchParam;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.TrainerDto;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public String reservation(ReservationParam param, Principal principal, Model model)
		throws ParseException {

		if (param.getDay() == -1) {
			throw new ReservationException(EMPTY_SCHEDULE.message());
		}


		ScheduleDto schedule = reservationService.getSchedule(
			parseDate(param.getYear(), param.getCurrentMonth(), param.getDay()),
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

	@PostMapping("/add")
	public String addReservation(ReservationParam param, Principal principal, Model model) {
		reservationService.addReservation(param, principal.getName());

		return "redirect:/";
	}

	@GetMapping("/view")
	public String viewReservation(Principal principal, Model model) {
		principal.getName();
		List<ReservationDto> reservationList = reservationService.getReservationList(
			principal.getName());
		model.addAttribute("reservations", reservationList);
		return "/reservation/list";
	}

	@GetMapping("/error")
	public String error(@RequestParam(required = false) String errorMessage, Model model) {
		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}
		return "/error/error";
	}
}
