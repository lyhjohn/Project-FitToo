package com.fittoo.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/error")
@Controller
public class ErrorController {

	@GetMapping("/commonErrorPage")
	public String errorPage(@RequestParam String errorMessage, Model model) {
		model.addAttribute("errorMessage", errorMessage);
		return "error/error";
	}
}
