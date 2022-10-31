package com.fittoo.exception;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.ui.Model;
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
	public String findException(LoginFailException e) {

		log.info("UserNotFoundException.message={}", e.getMessage());
		return "redirect:/logout";
	}

	@ExceptionHandler(DateParseException.class)
	public String dateParseException(DateParseException e,
		RedirectAttributes attributes) {

		log.info("DateParseException.message={}", e.getMessage());

		attributes.addAttribute("errorMessage", e.getMessage());

		return "redirect:/trainer/schedule";
	}

	@ExceptionHandler(UserIdAlreadyExist.class)
	public String dateParseException(UserIdAlreadyExist e, Model model, RedirectAttributes attributes) {

		log.info("UserIdAlreadyExist.message={}", e.getMessage());

		model.addAttribute("errorMessage", e.getMessage());
		attributes.addAttribute("member", e.getMemberInput());
		return "redirect:/member/register";
	}
}
