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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
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

        Optional<Principal> optionalPrincipal = Optional.ofNullable(principal);
        if (optionalPrincipal.isPresent() && input.getUserId() == null) {

            if (optionalPrincipal.get().getName() != null) {
                Map<String, Object> typeMap = typeCheckMap(principal.getName());

                if (typeMap.containsKey("member")) {

                    MemberDto member = (MemberDto) typeMap.get("member");
                    model.addAttribute("member", member);
                    return "/loginHome";
                }
                if (typeMap.containsKey("trainer")) {

                    TrainerDto trainer = (TrainerDto) typeMap.get("trainer");
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
            MemberDto member = memberService.findMember(input.getUserId());
            if (!MemberDto.checkLoginType(member, request, model)) {
                return "/login/loginForm";
            }
            return "/loginHome";
        }

        if (input.getLoginType().equals("trainer")) {
            TrainerDto trainer = trainerService.findTrainer(input.getUserId());
            if (!TrainerDto.checkLoginType(trainer, request, model)) {
                return "/login/loginForm";
            }
            return "/loginHome";
        }
        return "/home";
    }

    @GetMapping("/register")
    public String register() {

        return "/register";
    }
}
