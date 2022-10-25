package com.fittoo.member.controller;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.model.DateParam;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.member.service.MemberService;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.utills.CalendarUtil;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final TrainerService trainerService;

	@ModelAttribute(name = "regPurposes")
	private static Map<String, String> getRegPurposeMap() {
		Map<String, String> regPurposeMap = new LinkedHashMap<>();
		regPurposeMap.put("diet", "다이어트");
		regPurposeMap.put("weight", "웨이트");
		regPurposeMap.put("rehabilitation", "재활");
		regPurposeMap.put("health", "체력");
		regPurposeMap.put("partner_training", "파트너트레이닝");
		return regPurposeMap;
	}

	@GetMapping("/register")
	public String register(@ModelAttribute(name = "member") MemberInput memberInput, Model model) {
		return "/member/register";
	}


	@PostMapping("/register")
	public String registerComplete(
		@ModelAttribute(name = "member") @Validated MemberInput memberInput,
		BindingResult bindingResult, Model model) {
		ServiceResult result = memberService.memberRegister(memberInput);

		if (bindingResult.hasErrors()) {
			log.info("error={}", bindingResult);
			return "/member/register";
		}

		if (!result.isResult()) {
			model.addAttribute("errorMessage", result.getErrorMessage().message());
			return "/member/register";
		}
		return "redirect:/";
	}


	@GetMapping("/calendar")
	public String calendar(ReservationParam param, Principal principal, Model model,
		@RequestParam(required = false) Integer prevMonth,
		@RequestParam(required = false) Integer nextMonth,
		@RequestParam(required = false) Integer year,
		@RequestParam(required = false) String trainerId) {



		if (param != null) {
			TrainerDto trainer = trainerService.findTrainer(param.getTrainerId());
			model.addAttribute("trainerId", param.getTrainerId());
		}
		if (trainerId != null) {
			TrainerDto trainer = trainerService.findTrainer(trainerId);
			model.addAttribute("trainerId", trainerId);
		}

		Map<Integer, String> dayMap = new HashMap<>();

		if (prevMonth != null) {
			int[] curMonthAndYear = CalendarUtil.getNewMonthAndYear(year, prevMonth);
			dayMap = CalendarUtil.getPrevMonthMap(year, prevMonth);
			model.addAttribute("year", curMonthAndYear[0]);
			model.addAttribute("currentMonth", curMonthAndYear[1]);
		}

		if (nextMonth != null) {
			dayMap = CalendarUtil.getNextMonthMap(year, nextMonth);
			int[] curMonthAndYear = CalendarUtil.getNewMonthAndYear(year, nextMonth);
			model.addAttribute("year", curMonthAndYear[0]);
			model.addAttribute("currentMonth", curMonthAndYear[1]);
		}

		if (prevMonth == null && nextMonth == null) {
			dayMap = CalendarUtil.getMonthMap(LocalDate.now().getYear(),
				LocalDate.now().getMonthValue());
			model.addAttribute("currentMonth", LocalDate.now().getMonthValue());
			model.addAttribute("year", LocalDate.now().getYear());
		}
		model.addAttribute("dayMap", dayMap);
		return "/reservation/calendar";
	}

	@PostMapping("/reservation")
	public String reservation(ReservationParam param, Principal principal, Model model,
		String date) {
		System.out.println("param.getTrainerId() = " + param.getTrainerId());
		model.addAttribute("trainerId", param.getTrainerId());
		model.addAttribute("date", date);
		return "/reservation/reservationForm";
	}

	@PostMapping(value = {"/calendar/prev", "/calendar/next"})
	public String prevCalendar(RedirectAttributes redirectAttributes, DateParam dateParam,
		ReservationParam param) {
		if (dateParam.getPrevMonth() != null) {
			redirectAttributes.addAttribute("prevMonth", dateParam.getPrevMonth());
		} else {
			redirectAttributes.addAttribute("nextMonth", dateParam.getNextMonth());
		}
		redirectAttributes.addAttribute("year", dateParam.getYear());
		redirectAttributes.addAttribute("trainerId", param.getTrainerId());
		return "redirect:/member/calendar";
	}
}
