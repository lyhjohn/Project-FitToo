package com.fittoo.web.controller;

import com.fittoo.web.model.LoginInfo;
import com.fittoo.web.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_COMPLETE, required = false) LoginInfo loginInfo,
                       Model model) {
        if (loginInfo == null) {

            return "home";
        }

        model.addAttribute("member", loginInfo);
        return "/loginHome";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }
}
