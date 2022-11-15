package com.fittoo.member.controller;

import static com.fittoo.common.message.CommonErrorMessage.ABNORMAL_APPROACH;
import static com.fittoo.trainer.model.TrainerDto.whatIsGender;

import com.fittoo.exception.AuthException;
import com.fittoo.member.model.DateParam;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.member.service.MemberService;
import com.fittoo.reservation.util.SchedulableDateMark;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.UpdateInput;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.utills.CalendarUtil;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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

	@ModelAttribute(name = "loginType")
	private String getLoginType() {
		return "member";
	}

	@GetMapping("/register")
	public String register(Model model, @RequestParam(required = false) String errorMessage) {

		if (StringUtils.hasText(errorMessage)) {
			model.addAttribute("errorMessage", errorMessage);
		}

		return "/member/register";
	}


	@PostMapping("/register")
	public String registerComplete(
		@ModelAttribute(name = "member") @Validated MemberInput memberInput,
		BindingResult bindingResult, Model model) {
		memberService.memberRegister(memberInput);
		if (bindingResult.hasErrors()) {
			log.info("error={}", bindingResult);
			return "/member/register";
		}

		return "redirect:/";
	}

	@GetMapping("/profile")
	public String profile(Principal principal, @RequestParam(required = false) String errorMessage,
		Model model) {

		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}

		MemberDto member = memberService.findMember(principal.getName());
		MemberDto.whatIsGender(member.getGender(), model);

		model.addAttribute("member", member);
		return "/member/profile";
	}

	@PostMapping("/profileUpdate")
	public String profileUpdate(@Validated @ModelAttribute UpdateInput input,
		BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			whatIsGender(input.getGender(), model);
			model.addAttribute("member", input);
			return "/member/profile";
		}
		MemberDto member = memberService.update(input);
		whatIsGender(input.getGender(), model);
		model.addAttribute("member", member);

		return "/member/profile";
	}

	@PostMapping("/withdraw")
	public String withdraw(Principal principal, String userId) {
		if (!principal.getName().equals(userId)) {
			throw new AuthException(ABNORMAL_APPROACH);
		}
		memberService.withdraw(userId);

		return "redirect:/logout";
	}

	@GetMapping("/calendar")
	public String calendar(ReservationParam param, Principal principal, Model model,
		@RequestParam(required = false) Integer prevMonth,
		@RequestParam(required = false) Integer nextMonth,
		@RequestParam(required = false) Integer year, String trainerId) {

		if (param != null) {
			TrainerDto trainer = trainerService.findTrainer(param.getTrainerId());
			model.addAttribute("trainerId", param.getTrainerId());
		}
		if (trainerId != null) {

			/**
			 * 트레이너가 예약 가능한 날짜로 등록한 날은 캘린더에 파란 글씨로 표시할 수 있게 Map을 만들어 view로 전달
			 */
			Map<Integer, Boolean> canReserveDayMap = SchedulableDateMark.canReserveDate(
				CalendarUtil.pageControl(prevMonth, nextMonth, year, model), trainerId,
				trainerService.showSchedule(trainerId));

			model.addAttribute("canReserveDay", canReserveDayMap);
			model.addAttribute("trainerId", trainerId);
		}

		return "/reservation/calendar";
	}

	@PostMapping(value = {"/calendar/prev", "/calendar/next"})
	public String calendarPage(RedirectAttributes redirectAttributes, DateParam dateParam,
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
