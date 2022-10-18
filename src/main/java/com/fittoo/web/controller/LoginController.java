package com.fittoo.web.controller;

import com.fittoo.web.model.LoginInput;
import com.fittoo.web.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @RequestMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginInput input) {

        return "/login/loginForm";
    }

    @GetMapping("/logout")
    public String logout() {

        return "redirect:/";
    }
}
