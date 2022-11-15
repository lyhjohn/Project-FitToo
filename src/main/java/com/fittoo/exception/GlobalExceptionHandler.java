package com.fittoo.exception;

import static com.fittoo.common.message.ReservationErrorMessage.ALREADY_CANCELED_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.ALREADY_COMPLETED_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.ALREADY_END_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.CAN_NOT_COMPLETE_BEFORE_RESERVATION_DATE;
import static com.fittoo.common.message.ReservationErrorMessage.INVALID_RESERVATION;
import static com.fittoo.common.message.ReservationErrorMessage.ONLY_COMPLETE_STATUS_CAN_BE_END;
import static com.fittoo.common.message.ReservationErrorMessage.PROHIBIT_RESERVATION_CANCEL_THREE_DAYS_AGO;
import static com.fittoo.common.message.ReviewErrorMessage.NOT_FOUND_REVIEW;
import static com.fittoo.member.model.LoginType.NORMAL;
import static com.fittoo.member.model.MemberDto.whatIsGender;

import com.fittoo.common.message.ReservationErrorMessage;
import com.fittoo.common.message.ScheduleErrorMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
@Getter
public class GlobalExceptionHandler extends SimpleUrlAuthenticationFailureHandler {

	private final String MEMBER = "member";
	private final String TRAINER = "trainer";

	@ExceptionHandler(LoginFailException.class)
	public String loginException(LoginFailException e, RedirectAttributes attributes) {
		log.info("LoginFailException.message={}", e.getErrorMessage());

		attributes.addAttribute("errorMessage", e.getErrorMessage().message());
		return "redirect:/logout";
	}

	@ExceptionHandler(AuthException.class)
	public String authException(AuthException e, RedirectAttributes attributes) {
		log.info("AuthException.message={}", e.getErrorMessage());
		attributes.addAttribute("errorMessage", e.getErrorMessage().message());
		return "redirect:/logout";
	}

	@ExceptionHandler(UserNotFoundException.class)
	public String findInfoException(UserNotFoundException e) {

		log.info("UserNotFoundException.message={}", e.getErrorMessage());

		return "redirect:/logout";
	}

	@ExceptionHandler(ScheduleException.class)
	public String scheduleException(ScheduleException e,
		RedirectAttributes attributes) {

		log.info("ScheduleException.message={}", e.getErrorMessage());
		if (e.getErrorMessage().equals(ScheduleErrorMessage.EMPTY_SCHEDULE)) {
			attributes.addAttribute("errorMessage", e.getErrorMessage().message());

			return "redirect:/reservation/empty";
		}

		attributes.addAttribute("errorMessage", e.getErrorMessage().message());

		return "redirect:/trainer/schedule";
	}

	@ExceptionHandler(RegisterException.class)
	public String registerException(RegisterException e, RedirectAttributes attributes) {

		log.info("RegisterException.message={}", e.getErrorMessage());

		attributes.addAttribute("errorMessage", e.getErrorMessage().message());

		if (e.getLoginType().equals(MEMBER)) {
			attributes.addFlashAttribute(MEMBER, e.getMemberInput());
			whatIsGender(e.getMemberInput().getGender(), attributes);

		} else if (e.getLoginType().equals(TRAINER)) {
			attributes.addFlashAttribute(TRAINER, e.getTrainerInput());
			whatIsGender(e.getTrainerInput().getGender(), attributes);
			return "redirect:/trainer/register";
		}
		return "redirect:/member/register";
	}

	@ExceptionHandler(FileException.class)
	public String fileException(FileException e, RedirectAttributes attributes) {

		log.info("FileException.message={}", e.getErrorMessage());

		attributes.addAttribute("errorMessage", e.getErrorMessage().message());

		return "redirect:/trainer/trainerList";
	}

	@ExceptionHandler(ReservationException.class)
	public String reservationException(ReservationException e, RedirectAttributes attributes) {

		log.info("ReservationException.message={}", e.getErrorMessage());
		attributes.addAttribute("errorMessage", e.getErrorMessage().message());

		if (e.getErrorMessage().equals(INVALID_RESERVATION)) {
			return "redirect:/error/commonErrorPage";
		}

		if (e.getErrorMessage().equals(ReservationErrorMessage.EMPTY_SCHEDULE)) {
			return "redirect:/reservation/empty";
		}

		if (e.getErrorMessage().equals(PROHIBIT_RESERVATION_CANCEL_THREE_DAYS_AGO)) {
			return "redirect:/reservation/view";
		}

		if (e.getErrorMessage().equals(ALREADY_COMPLETED_RESERVATION) ||
			e.getErrorMessage().equals(ALREADY_CANCELED_RESERVATION) ||
			e.getErrorMessage().equals(ALREADY_END_RESERVATION) ||
			e.getErrorMessage().equals(CAN_NOT_COMPLETE_BEFORE_RESERVATION_DATE) ||
			e.getErrorMessage().equals(ONLY_COMPLETE_STATUS_CAN_BE_END)) {
			attributes.addAttribute("errorMessage", e.getErrorMessage().message());
			attributes.addAttribute("memberId", e.getMemberId());
			attributes.addAttribute("reservationId", e.getReservationId());
			return "redirect:/trainer/view/reservation_member/{memberId}/{reservationId}";
		}

		if (e.getLoginType() != null) {
			if (e.getLoginType().equals(NORMAL) && e.getErrorMessage()
				.equals(ALREADY_CANCELED_RESERVATION)) {
				attributes.addAttribute("errorMessage", e.getErrorMessage().message());
				return "redirect:/reservation/view";
			}
		}
		return "redirect:/reservation/error";
	}

	@ExceptionHandler(WithdrawException.class)
	public String withDrawException(WithdrawException e, RedirectAttributes attributes) {
		log.info("WithdrawException.message={}", e.getErrorMessage());
		attributes.addAttribute("errorMessage", e.getErrorMessage().message());
		return "redirect:/member/profile";
	}

	@ExceptionHandler(ReviewException.class)
	public String reviewException(ReviewException e, RedirectAttributes attributes) {
		log.info("ReviewException.message={}", e.getErrorMessage());
		attributes.addAttribute("errorMessage", e.getErrorMessage().message());
		if (e.getErrorMessage().equals(NOT_FOUND_REVIEW)) {
			return "redirect:/error/commonErrorPage";
		}
		return "redirect:/reservation/view";
	}
}
