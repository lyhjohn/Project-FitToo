//package com.fittoo.configuration;
//
//import com.fittoo.common.message.ErrorMessage;
//import com.fittoo.exception.LoginFailException;
//import com.fittoo.member.model.MemberDto;
//import com.fittoo.member.service.MemberService;
//import com.fittoo.member.service.impl.MemberServiceImpl;
//import com.fittoo.trainer.model.TrainerDto;
//import com.fittoo.trainer.service.TrainerService;
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//
//
//@Slf4j
//@RequiredArgsConstructor
//public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//
//	private final MemberService memberService;
//
//	private final TrainerService trainerService;
//
//
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//		Authentication authentication) throws IOException, ServletException {
//
//		String loginType = request.getParameter("loginType");
//		String userId = request.getParameter("userId");
//		String errorMessage = ErrorMessage.INVALID_ID_OR_PWD.message();
//
//		if (loginType.equals("member")) {
//			MemberDto member = memberService.findMember(userId);
//			if (member == null) {
//				request.setAttribute("errorMessage", errorMessage);
//				throw new LoginFailException(ErrorMessage.INVALID_ID_OR_PWD.message());
//			}
//		} else if (loginType.equals("trainer")) {
//			TrainerDto trainer = trainerService.findTrainer(userId);
//			if (trainer == null) {
//				request.setAttribute("errorMessage", errorMessage);
//				System.out.println("로그인실패");
//				throw new LoginFailException(ErrorMessage.INVALID_ID_OR_PWD.message());
//			}
//		}
//
//		log.info("로그인성공");
//
//		super.onAuthenticationSuccess(request, response, authentication);
//	}
//}
