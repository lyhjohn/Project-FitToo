package com.fittoo.exception;

import com.fittoo.common.message.ErrorMessage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends SimpleUrlAuthenticationFailureHandler {

	@ExceptionHandler(LoginFailException.class)
	public String loginException(LoginFailException e, Model model, HttpServletRequest request,
		RedirectAttributes attributes)
		throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		log.info("errorMessage.log={}", e.getMessage());
		return "redirect:/logout";
	}
}
