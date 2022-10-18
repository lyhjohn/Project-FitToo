//package com.fittoo.configuration.auth;
//
//import com.fittoo.common.message.ErrorMessage;
//import com.fittoo.configuration.UserAuthenticationFailureHandler;
//import com.fittoo.exception.UserNotFound;
//import com.fittoo.member.entity.Member;
//import com.fittoo.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import javax.annotation.PostConstruct;
//import java.util.Optional;
//
///**
// * 시큐리티 설정에서 loginProcessingUrl("/login") 요청이 오면
// * 자동으로 UserDetailsService 타입으로 IoC에 빈으로 등록되어있는 loadUserByUsername 함수가 실행됨
// */
//@Service // 빈 등록
//@RequiredArgsConstructor
//public class PrincipalDetailsService implements UserDetailsService {
//    private final MemberRepository memberRepository;
//
//
//    // 시큐리티 세션 = UserDetails가 들어가는 Authentication 타입
//    // 리턴된 UserDetails가 Autentication 내부에 들어감.
//    // Authentication은 시큐리티 세션 내부에 들어감
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
//        if (optionalMember.isEmpty()) {
//            new UserAuthenticationFailureHandler();
//        }
//
//        return optionalMember.map(PrincipalDetails::new).orElseThrow(() -> new UsernameNotFoundException(userId));
//    }
//}
