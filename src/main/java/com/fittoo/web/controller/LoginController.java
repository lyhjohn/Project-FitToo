package com.fittoo.web.controller;

import com.fittoo.configuration.UserAuthenticationFailureHandler;
import com.fittoo.web.model.LoginInput;
import com.fittoo.web.service.LoginService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @RequestMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginInput input) {

        return "/login/loginForm";
    }

//    @RequestMapping("/logout/complete")
//    public String logout(@RequestParam(name = "errorMessage") String errorMessage, HttpServletRequest request) {
//        System.out.println("errorMessage = " + errorMessage);
//        System.out.println("request.getAttribute() = " + request.getAttribute("errorMessage"));
//        return "/login/loginForm";
//    }
}
