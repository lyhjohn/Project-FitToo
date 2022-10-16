package com.fittoo.web.controller;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.web.model.LoginInput;
import com.fittoo.web.model.LoginInfo;
import com.fittoo.web.service.LoginService;
import com.fittoo.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginInput input) {

        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String loginComplete(@ModelAttribute("loginForm") LoginInput input, HttpServletRequest request, Model model) {

        LoginInfo loginInfo = loginService.loginSubmit(input);
        if (loginInfo == null) {
            ServiceResult result = new ServiceResult(false, ErrorMessage.INVALID_ID_OR_PWD);
            model.addAttribute("errorMessage", result.getErrorMessage().description());
            return "/login/loginForm";
        }


        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_COMPLETE, loginInfo);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
