package com.fittoo.exception;

import static com.fittoo.member.model.MemberDto.whatIsGender;

import com.fittoo.common.message.ReservationErrorMessage;
import com.fittoo.common.message.ScheduleErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends SimpleUrlAuthenticationFailureHandler {

	@ExceptionHandler(LoginFailException.class)
	public String loginException(LoginFailException e, RedirectAttributes attributes) {
		log.info("LoginFailException.message={}", e.getMessage());

		attributes.addAttribute("errorMessage", e.getMessage());
		return "redirect:/logout";
	}

	@ExceptionHandler(UserNotFoundException.class)
	public String findInfoException(UserNotFoundException e) {

		log.info("UserNotFoundException.message={}", e.getMessage());

		return "redirect:/logout";
	}

	@ExceptionHandler(ScheduleException.class)
	public String scheduleException(ScheduleException e,
		RedirectAttributes attributes) {

		log.info("ScheduleException.message={}", e.getMessage());
		if (e.getMessage().equals(ScheduleErrorMessage.EMPTY_SCHEDULE.message())) {
			attributes.addAttribute("errorMessage", e.getMessage());

			return "redirect:/reservation/empty";
		}

		attributes.addAttribute("errorMessage", e.getMessage());

		return "redirect:/trainer/schedule";
	}

	@ExceptionHandler(RegisterException.class)
	public String registerException(RegisterException e, RedirectAttributes attributes) {

		log.info("RegisterException.message={}", e.getMessage());

		attributes.addAttribute("errorMessage", e.getMessage());

		if (e.getLoginType().equals("member")) {
			attributes.addFlashAttribute("member", e.getMemberInput());
			whatIsGender(e.getMemberInput().getGender(), attributes);

		} else if (e.getLoginType().equals("trainer")) {
			attributes.addFlashAttribute("trainer", e.getTrainerInput());
			whatIsGender(e.getMemberInput().getGender(), attributes);
			return "redirect:/trainer/register";
		}
		return "redirect:/member/register";
	}

	@ExceptionHandler(FileException.class)
	public String fileException(FileException e, RedirectAttributes attributes) {

		log.info("FileException.message={}", e.getMessage());

		attributes.addAttribute("errorMessage", e.getMessage());

		return "redirect:/trainer/trainerList";
	}

	@ExceptionHandler(ReservationException.class)
	public String reservationException(ReservationException e, RedirectAttributes attributes) {

		log.info("ReservationException.message={}", e.getMessage());
		attributes.addAttribute("errorMessage", e.getMessage());

		if (e.getMessage().equals(ReservationErrorMessage.EMPTY_SCHEDULE.message())) {
			return "redirect:/reservation/empty";
		}

		return "redirect:/reservation/error";
	}
}
