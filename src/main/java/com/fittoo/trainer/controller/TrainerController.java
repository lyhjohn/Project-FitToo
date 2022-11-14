package com.fittoo.trainer.controller;


import static com.fittoo.common.message.FileErrorMessage.INVALID_FILE;
import static com.fittoo.common.message.FileErrorMessage.INVALID_PROFILE_PICTURE;
import static com.fittoo.common.message.FindErrorMessage.NOT_FOUND_TRAINER;
import static com.fittoo.trainer.model.TrainerDto.whatIsGender;
import static com.fittoo.utills.CalendarUtil.StringOrIntegerToLocalDate.parseDate;
import static java.time.LocalDate.now;

import com.fittoo.exception.FileException;
import com.fittoo.member.model.DateParam;
import com.fittoo.member.model.MemberDto;
import com.fittoo.member.model.ReservationParam;
import com.fittoo.member.service.MemberService;
import com.fittoo.page.model.TrainerPageParam;
import com.fittoo.reservation.model.ReservationDto;
import com.fittoo.reservation.model.SearchParam;
import com.fittoo.reservation.service.ReservationService;
import com.fittoo.reservation.util.SchedulableDateMark;
import com.fittoo.trainer.model.ScheduleDto;
import com.fittoo.trainer.model.ScheduleInput;
import com.fittoo.trainer.model.TrainerDto;
import com.fittoo.trainer.model.TrainerInput;
import com.fittoo.trainer.model.UpdateInput;
import com.fittoo.trainer.service.TrainerService;
import com.fittoo.utills.CalendarUtil;
import com.fittoo.utills.PageUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
	private final ReservationService reservationService;
	private final MemberService memberService;

	@ModelAttribute(name = "loginType")
	private String getLoginType() {
		return "trainer";
	}

	@ModelAttribute(name = "exerciseType")
	private static Map<String, String> getExerciseType() {
		Map<String, String> exerciseType = new LinkedHashMap<>();
		exerciseType.put("PT", "헬스 PT");
		exerciseType.put("pilates", "필라테스");
		exerciseType.put("yoga", "요가");
		exerciseType.put("golf", "골프");
		exerciseType.put("pole_dance", "폴댄스");
		exerciseType.put("crossfit", "크로스핏");
		exerciseType.put("rehabilitation", "재활");
		exerciseType.put("boxing", "복싱");
		return exerciseType;
	}


	@GetMapping("/register")
	public String register(Model model, @RequestParam(required = false) String errorMessage) {
		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}
		return "/trainer/register";
	}

	@PostMapping("/register")
	public String registerComplete(
		@ModelAttribute(name = "trainer") @Validated TrainerInput trainerInput,
		BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/trainer/register";
		}

		trainerService.trainerRegister(trainerInput);

		return "redirect:/";
	}

	@GetMapping("/profile")
	public String profile(Model model, HttpServletRequest request, Principal principal,
		@RequestParam(required = false) String errorMessage) {

		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}

		String userId = principal.getName();
		TrainerDto trainer = trainerService.findTrainer(userId);

		whatIsGender(trainer.getGender(), model);

		model.addAttribute("member", trainer);
		return "/trainer/profile";
	}

	/**
	 * 프로필사진 불러오는 메서드
	 */
	@ResponseBody // 파일 이미지 띄우기
	@GetMapping("/images/{filename}")
	public Resource getImage(@PathVariable String filename) {
		log.info("filename={}", getFullPath(filename, "trainer"));

		try {
			return new UrlResource("file:" + getFullPath(filename, "trainer"));
		} catch (IOException e) {
			throw new FileException(INVALID_FILE.message(), new FileNotFoundException());
		}
	}

	public String getFullPath(String fileName, String loginType) {
		String fileDir = "C:/inflearn/image";
		String dirs = String.format("%s\\%s\\%d\\%02d\\%02d\\",
			fileDir, loginType, now().getYear(), now().getMonthValue(), now().getDayOfMonth());

		return dirs + fileName;
	}

	@PostMapping("/profileUpdate")
	public String profileUpdate(@Validated @ModelAttribute(name = "member") UpdateInput input,
		BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			whatIsGender(input.getGender(), model);
			model.addAttribute("member", input);
			return "/trainer/profile";
		}

		TrainerDto trainer = trainerService.update(input);

		whatIsGender(input.getGender(), model);

		model.addAttribute("member", trainer);
		return "/trainer/profile";
	}

	@PostMapping("profilePicture/update")
	public String profilePictureUpdate(MultipartFile file, Principal principal, Model model) {
		TrainerDto trainer = trainerService.updateProfilePicture(file, principal.getName());
		if (trainer == null) {
			model.addAttribute("errorMessage", INVALID_PROFILE_PICTURE);
			return "/error/error";
		}
		return "redirect:/trainer/profile";
	}

	@GetMapping("/trainerList")
	public String trainerList(Principal principal, Model model,
		@RequestParam(required = false) String trainerId,
		@RequestParam(required = false) String errorMessage,
		SearchParam searchParam, TrainerPageParam page, @ModelAttribute String loginType) {

		if (trainerId != null) {
			TrainerDto trainer = trainerService.findTrainer(trainerId);
			model.addAttribute("trainerDetail", trainer);
		}

		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}

		Long totalCount = trainerService.getTotalCountTrainerList();
		int curPage = PageUtil.getCurPageAndAttributePageList(page, model, totalCount);

		List<TrainerDto> trainerList;
		if (isSearch(searchParam)) {
			trainerList = reservationService.searchTrainer(searchParam);
		} else {
			trainerList = trainerService.findTrainersByClickPage(curPage);
		}
		model.addAttribute("trainerList", trainerList);
		return "/trainer/trainerList";
	}

	private boolean isSearch(SearchParam param) {
		return param.getSearchWord() != null;
	}

	@GetMapping("/detail")
	public String trainerDetail(String userId, RedirectAttributes redirectAttributes) {
		if (userId == null) {
			redirectAttributes.addAttribute("errorMessage", NOT_FOUND_TRAINER);
			return "redirect:/trainer/trainerList";
		}
		redirectAttributes.addAttribute("trainerId", userId);
		return "redirect:/trainer/trainerList";
	}

	@GetMapping("/schedule")
	public String scheduleManager(Principal principal, Model model,
		@RequestParam(required = false) Integer prevMonth,
		@RequestParam(required = false) Integer nextMonth,
		@RequestParam(required = false) Integer year,
		@RequestParam(required = false) String errorMessage) {

		model.addAttribute("loginType", "트레이너");
		model.addAttribute("trainerId", principal.getName());

		if (StringUtils.hasText(errorMessage)) {
			model.addAttribute("errorMessage", errorMessage);
		}

		List<ScheduleDto> scheduleList = trainerService.showSchedule(principal.getName());

		model.addAttribute("scheduleList", scheduleList);

		Map<Integer, Boolean> canReserveDayMap = SchedulableDateMark.canReserveDate(
			CalendarUtil.pageControl(prevMonth, nextMonth, year, model), principal.getName(),
			scheduleList);

		model.addAttribute("canReserveDay", canReserveDayMap);

		return "trainer/schedule/schedule";
	}

	@PostMapping(value = {"/calendar/prev", "/calendar/next"})
	public String calendarPage(RedirectAttributes redirectAttributes, DateParam dateParam,
		ReservationParam param) {
		if (dateParam.getPrevMonth() != null) {
			redirectAttributes.addAttribute("prevMonth", dateParam.getPrevMonth());
		} else {
			redirectAttributes.addAttribute("nextMonth", dateParam.getNextMonth());
		}
		redirectAttributes.addAttribute("year", dateParam.getYear());
		redirectAttributes.addAttribute("trainerId", param.getTrainerId());
		return "redirect:/trainer/schedule";
	}

	@PostMapping("/schedule/create")
	public String createSchedule(Principal principal, @ModelAttribute ScheduleInput input,
		Model model) throws ParseException {

		trainerService.createSchedule(principal.getName(), input);

		return "redirect:/trainer/schedule";
	}

	@PostMapping("/schedule/delete")
	public String deleteSchedule(String date, String trainerId) throws ParseException {
		trainerService.deleteSchedule(date, trainerId);
		return "redirect:/trainer/schedule";
	}

	@PostMapping("/view/reservation_member")
	public String getReservationMember(ReservationParam param, RedirectAttributes attributes)
		throws ParseException {

		List<ReservationDto> reservationList = reservationService.viewReservationsByMember(param);

		attributes.addFlashAttribute("reservations", reservationList);

		return "redirect:/trainer/schedule";
	}

	@GetMapping("/view/reservation_member/{memberId}/{reservationId}")
	public String reservationMemberDetail(@PathVariable String memberId,
		@PathVariable Long reservationId,
		@RequestParam(required = false) String errorMessage, Model model) {

		if (StringUtils.hasText(errorMessage)) {
			model.addAttribute("errorMessage", errorMessage);
		}

		MemberDto member = memberService.findMember(memberId);
		model.addAttribute("member", member);
		model.addAttribute("reservationId", reservationId);
		return "trainer/schedule/reservation_member_detail";
	}

	/**
	 * 리뷰 목록에서 트레이너 아이디 클릭 시 트레이너 기본정보보기, 예약하기 가능
	 */
	@GetMapping("/simpleInfo")
	public String simpleInfo(@RequestParam String trainerId, Model model) {
		TrainerDto trainer = trainerService.findTrainer(trainerId);
		model.addAttribute("trainer", trainer);
		return "trainer/simpleInfo";
	}
}
