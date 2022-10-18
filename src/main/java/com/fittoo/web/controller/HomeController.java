package com.fittoo.web.controller;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.service.MemberService;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.web.model.LoginInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberService memberService;
    private final TrainerService trainerService;


    public Map<String, Object> typeCheckMap(String userId) {
        Map<String, Object> map = new HashMap<>();

        TrainerDto trainer = trainerService.findTrainer(userId);
        if (trainer == null) {
            MemberDto member = memberService.findMember(userId);
            if (member.getUserId() != null) {
                map.put("member", member);
                return map;
            }
        }

        map.put("trainer", trainer);
        return map;
    }

    @RequestMapping("/")
    public String home(LoginInput input, Model model, HttpServletRequest request, Principal principal) {

        Optional<Principal> principal1 = Optional.ofNullable(principal);

        if (principal1.isPresent() && input.getUserId() == null) {
            if (principal1.get().getName() != null) {
                Map<String, Object> typeMap = typeCheckMap(principal.getName());
                if (typeMap.containsKey("member")) {
                    MemberDto member = (MemberDto) typeMap.get("member");
                    System.out.println("2 = " + principal1.get().getName());
                    model.addAttribute("member", member);
                    return "/loginHome";
                }
                if (typeMap.containsKey("trainer")) {
                    TrainerDto trainer = (TrainerDto) typeMap.get("trainer");
                    System.out.println("2 = " + principal1.get().getName());
                    model.addAttribute("member", trainer);
                    return "/loginHome";
                }
            }
        }

        if (input.getUserId() == null) {
            HttpSession session = request.getSession();
            session.invalidate();
            return "/home";
        }

        if (input.getLoginType().equals("member")) {
            System.out.println("1차검문소");
            MemberDto member = memberService.findMember(input.getUserId());
            System.out.println("2차검문소");
            if (member == null) {
                System.out.println("3차검문소");
                model.addAttribute("errorMessage", ErrorMessage.INVALID_ID_OR_PWD.description());
                System.out.println("4차검문소");
                HttpSession session = request.getSession();
                System.out.println("5차검문소");
                session.invalidate();
                System.out.println("6차검문소");
                return "/login/loginForm";
            }

            model.addAttribute("member", member);
            return "/loginHome";
        }

        if (input.getLoginType().equals("trainer")) {
            TrainerDto trainer = trainerService.findTrainer(input.getUserId());

            if (trainer == null) {
                model.addAttribute("errorMessage", ErrorMessage.INVALID_ID_OR_PWD.description());
                HttpSession session = request.getSession();
                session.invalidate();
                return "/login/loginForm";
            }
            model.addAttribute("member", trainer);
            return "/loginHome";
        }

        return "/home";
    }

    @GetMapping("/register")
    public String register() {

        return "/register";
    }
}
