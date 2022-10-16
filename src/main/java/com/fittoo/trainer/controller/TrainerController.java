package com.fittoo.trainer.controller;


import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.web.model.LoginInfo;
import com.fittoo.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainer")
@Slf4j
public class TrainerController {
    private final TrainerService trainerService;

    @ModelAttribute(name = "mainPtList")
    private static Map<String, String> getMainPtList() {
        Map<String, String> mainPtListMap = new LinkedHashMap<>();
        mainPtListMap.put("diet", "다이어트");
        mainPtListMap.put("weight", "웨이트");
        mainPtListMap.put("rehabilitation", "재활");
        mainPtListMap.put("health", "체력");
        mainPtListMap.put("partner_training", "파트너트레이닝");
        return mainPtListMap;
    }


    @GetMapping("/register")
    public String register(@ModelAttribute(name = "trainer") TrainerInput trainerInput) {

        return "/trainer/register";
    }

    @PostMapping("/register")
    public String registerComplete(@ModelAttribute @Valid TrainerInput trainerInput, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/register";
        }

        ServiceResult result = trainerService.trainerRegister(trainerInput);
        if (!result.isResult()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/register";
        }
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(String userId, Model model, HttpServletRequest request) {
        TrainerDto trainerDto = trainerService.lookUpProfile(userId);

        if (trainerDto == null) {
            model.addAttribute("errorMessage", ErrorMessage.ACCESS_REJECT.description());
            log.info("유저 정보 불일치");
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return "/error/error";
        }

        model.addAttribute("trainer", trainerDto);
        return "/trainer/profile";
    }
}
