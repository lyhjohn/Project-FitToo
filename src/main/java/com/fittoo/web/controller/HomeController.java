package com.fittoo.web.controller;

import static com.fittoo.common.message.LoginErrorMessage.INVALID_ID_OR_PWD;

import com.fittoo.exception.LoginFailException;
import com.fittoo.exception.UserNotFoundException;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.service.MemberService;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.web.model.LoginInput;
import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

	private final MemberService memberService;
	private final TrainerService trainerService;

	public Object typeCheck(String userId) {
		try {
			return trainerService.findTrainer(userId);
		} catch (UserNotFoundException e) {
			return  memberService.findMember(userId);
		}
	}

	@RequestMapping("/")
	public String home(LoginInput input, Model model, Principal principal,
		HttpServletRequest request) {
		Optional<Principal> optionalPrincipal = Optional.ofNullable(principal);
		if (optionalPrincipal.isPresent()) {
			model.addAttribute("member", typeCheck(principal.getName()));
			return "/home/loginHome";
		}

		if (input.getUserId() == null) {
			return "/home/home";
		}

		if (checkFailLoginType(input, model)) {
			return "redirect:/logout";
		}
		return "/home/loginHome";
	}

	@GetMapping("/register")
	public String register() {

		return "/register";
	}

	public boolean checkFailLoginType(LoginInput input, Model model) {
		if (input.getLoginType().equals("member")) {
			MemberDto member = memberService.findMember(input.getUserId());
			if (member == null) {
				throw new LoginFailException(INVALID_ID_OR_PWD.message());
			}
			model.addAttribute("member", member);
			return false;
		}

		if (input.getLoginType().equals("trainer")) {
			TrainerDto trainer = trainerService.findTrainer(input.getUserId());
			if (trainer == null) {
				throw new LoginFailException(INVALID_ID_OR_PWD.message());
			}
			model.addAttribute("member", trainer);
			return false;
		}
		return true;
	}
}

