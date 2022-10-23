package com.fittoo.trainer.controller;


import static com.fittoo.trainer.model.TrainerDto.whatIsGender;
import static java.time.LocalDate.now;

import com.fittoo.common.message.ErrorMessage;
import com.fittoo.common.model.ServiceResult;
import com.fittoo.member.model.LoginType;
import com.fittoo.member.service.MemberService;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import com.fittoo.trainer.service.TrainerService;
import java.net.MalformedURLException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainer")
@Slf4j
public class TrainerController {

	private final TrainerService trainerService;
	private final MemberService memberService;

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
	public String registerComplete(@ModelAttribute @Valid TrainerInput trainerInput,
		BindingResult bindingResult, Model model) {
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
	public String profile(Model model, HttpServletRequest request, Principal principal) {
		String userId = principal.getName();
		TrainerDto trainer = trainerService.findTrainer(userId);

		if (trainerIsNotFound(trainer, request)) {
			model.addAttribute("errorMessage", ErrorMessage.ACCESS_REJECT.description());
			return "/error/error";
		}

		whatIsGender(trainer.getGender(), model);

		model.addAttribute("member", trainer);
		return "/trainer/profile";
	}

	/**
	 * 프로필사진 불러오는 메서드
	 */
	@ResponseBody // 파일 이미지 띄우기
	@GetMapping("/images/{filename}")
	public Resource getImage(@PathVariable String filename) throws MalformedURLException {
		log.info("filename={}", getFullPath(filename, "trainer"));
		return new UrlResource("file:" + getFullPath(filename, "trainer"));
	}

	public String getFullPath(String fileName, String loginType) {
		String fileDir = "C:/inflearn/image";
		String dirs = String.format("%s\\%s\\%d\\%02d\\%02d\\",
			fileDir, loginType, now().getYear(), now().getMonthValue(), now().getDayOfMonth());

		return dirs + fileName;
	}

	@PostMapping("/profileUpdate")
	public String profileUpdate(@Validated @ModelAttribute(name = "member") UpdateInput input,
		BindingResult bindingResult,
		Model model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			log.info("error={}", bindingResult);
			whatIsGender(input.getGender(), model);
			model.addAttribute("member", input);
			return "/trainer/profile";
		}

		TrainerDto trainer = trainerService.update(input);

		if (trainerIsNotFound(trainer, request)) {
			return "redirect:/";
		}

		whatIsGender(input.getGender(), model);

		model.addAttribute("member", trainer);
		return "/trainer/profile";
	}

	@PostMapping("profilePicture/update")
	public String profilePictureUpdate(MultipartFile file, Principal principal, Model model) {
		TrainerDto trainer = trainerService.updateProfilePicture(file, principal.getName());
		if (trainer == null) {
			model.addAttribute("errorMessage", ErrorMessage.INVALID_PROFILE_PICTURE);
			return "/error/error";
		}
		return "redirect:/trainer/profile";
	}

	@GetMapping("/trainerList")
	public String trainerList(Principal principal, Model model,
		@RequestParam(required = false) String trainerId,
		@RequestParam(required = false) String errorMessage,
		LoginType loginType) {

		trainerExistAndTypeCheck(trainerId, errorMessage, loginType, model);

		List<TrainerDto> trainerList = trainerService.findAll();

		model.addAttribute("trainerList", trainerList);
		return "/trainer/trainerList";
	}

	private void trainerExistAndTypeCheck(String trainerId, String errorMessage,
		LoginType loginType, Model model) {
		if (trainerId != null) {
			TrainerDto trainer = trainerService.findTrainer(trainerId);
			System.out.println(
				"trainer.getProfilePictureNewName() = " + trainer.getProfilePictureNewName());
			model.addAttribute("loginType", trainer.getLoginType().description());
			model.addAttribute("trainerDetail", trainer);
		} else {
			model.addAttribute("loginType", loginType.description());
		}

		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}
	}

	@GetMapping("/detail")
	public String trainerDetail(String userId, RedirectAttributes redirectAttributes) {
		if (userId == null) {
			redirectAttributes.addAttribute("errorMessage", ErrorMessage.NOT_FOUND_TRAINER);
			return "redirect:/trainer/trainerList";
		}
		redirectAttributes.addAttribute("trainerId", userId);
		return "redirect:/trainer/trainerList";
	}


	private boolean trainerIsNotFound(TrainerDto trainer, HttpServletRequest request) {
		if (trainer == null) {
			HttpSession session = request.getSession(false);
			session.invalidate();
			return true;
		}
		return false;
	}

	@GetMapping("/schedule")
	public String scheduleManager(Principal principal, Model model) {
		String userId = principal.getName();
		ScheduleDto schedule = trainerService.showSchedule(userId);
		model.addAttribute("loginType", "트레이너");
		model.addAttribute("schedule", schedule);
		return "/trainer/schedule/schedule";
	}

	@PostMapping("/schedule/create")
	public String createSchedule(Principal principal, ScheduleInput input) {
		trainerService.createSchedule(principal.getName(), input);
		return "/trainer/schedule/schedule";
//		return "redirect:/trainer/schedule";
	}
}
