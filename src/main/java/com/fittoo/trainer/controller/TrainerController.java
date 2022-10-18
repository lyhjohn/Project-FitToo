package com.fittoo.trainer.controller;


import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import com.fittoo.trainer.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.fittoo.trainer.model.TrainerDto.*;
import static java.time.LocalDate.now;

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
    public String profile(TrainerInput input, Model model, HttpServletRequest request) {
        TrainerDto trainer = trainerService.findTrainer(input.getUserId());

        if (trainer == null) {
            model.addAttribute("errorMessage", ErrorMessage.ACCESS_REJECT.description());
            log.info("유저 정보 불일치");
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return "/error/error";
        }

        String gender = trainer.getGender();
        whatIsGender(input.getGender(), model);


        model.addAttribute("member", trainer);
        return "/trainer/profile";
    }

    public String getFullPath(String fileName, String loginType) {
        String fileDir = "C:/inflearn/image";
        String dirs = String.format("%s\\%s\\%d\\%02d\\%02d\\",
                fileDir, loginType, now().getYear(), now().getMonthValue(), now().getDayOfMonth());

        return dirs + fileName;
    }

    /**
     * 프로필사진 불러오는 메서드
     */
    @ResponseBody // 파일 이미지 띄우기
    @GetMapping("/images/{filename}")
    public Resource getImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + getFullPath(filename, "trainer"));
    }


    @PostMapping("/profileUpdate")
    public String profileUpdate(@Validated UpdateInput input, BindingResult bindingResult, Model model, HttpServletRequest request) {

        TrainerDto trainer = trainerService.update(input);
        String gender = trainer.getGender();

        if (bindingResult.hasErrors()) {


            whatIsGender(input.getGender(), model);
            model.addAttribute("member", input);
            return "/trainer/profile";
        }

        if (trainer == null) {
            HttpSession session = request.getSession();
            session.invalidate();
            return "redirect:/";
        }

        whatIsGender(input.getGender(), model);

        model.addAttribute("member", trainer);
        return "/trainer/profile";
    }


//    public void whatIsGender(Object gender, Model model) {
//
//        if (gender != null) {
//            if (gender instanceof String) {
//                if (gender.equals("남자")) {
//                    model.addAttribute("isMan", true);
//                } else {
//                    model.addAttribute("isGirl", true);
//                }
//            }
//
//            if (gender instanceof Integer) {
//                if ((int) gender == 1) {
//                    model.addAttribute("isMan", true);
//                } else {
//                    model.addAttribute("isGirl", true);
//                }
//            }
//        }
//    }
}
