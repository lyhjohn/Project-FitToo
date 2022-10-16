package com.fittoo.member.controller;

import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.model.MemberInput;
import com.fittoo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute(name = "regPurposes")
    private static Map<String, String> getRegPurposeMap() {
        Map<String, String> regPurposeMap = new LinkedHashMap<>();
        regPurposeMap.put("diet", "다이어트");
        regPurposeMap.put("weight", "웨이트");
        regPurposeMap.put("rehabilitation", "재활");
        regPurposeMap.put("health", "체력");
        regPurposeMap.put("partner_training", "파트너트레이닝");
        return regPurposeMap;
    }

    @GetMapping("/register")
    public String register(@ModelAttribute(name = "member") MemberInput memberInput, Model model) {

        return "/member/register";
    }


    @PostMapping("/register")
    public String registerComplete(@ModelAttribute(name = "member") @Validated MemberInput memberInput, BindingResult bindingResult, Model model) {
        System.out.println("memberInput = " + memberInput.getRegPurposeList());
        ServiceResult result = memberService.memberRegister(memberInput);

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "/member/register";
        }


        if (!result.isResult()) {
            model.addAttribute("errorMessage", result.getErrorMessage().description());
            return "/member/register";
        }

        return "redirect:/";
    }
}
