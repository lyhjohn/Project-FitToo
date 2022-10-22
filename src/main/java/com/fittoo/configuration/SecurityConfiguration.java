package com.fittoo.configuration;

import com.fittoo.web.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LoginService loginService;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico"); // 지정된 정적인 파일은 인증 무시
        web.ignoring().antMatchers("/inflearn/image/**");
        web.ignoring().antMatchers("/inflearn/**");
        web.ignoring().antMatchers("/templates/css/**");
        web.ignoring().antMatchers("/templates/js/**");
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 권한 필요 경로
                .antMatchers("/",
                        "/register",
                        "/member/register",
                        "/trainer/register",
                        "/trainer/images/**",
                        "/images/**",
                        "/templates/js/**",
                        "/logout")
                .permitAll(); // 경로에 접근 권한 허용 설정


        http.formLogin() // 로그인 작동하는 로그인페이지 임의로 구성
                .loginPage("/login")
                .failureHandler(getFailureHandler())
                .permitAll()
                .loginProcessingUrl("/login") // 해당 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                .usernameParameter("userId")
                .successForwardUrl("/"); // 로그인 성공 시 이동 url (POST)

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 요청 url 매핑
                .invalidateHttpSession(true) // 세션 초기화
                .logoutSuccessUrl("/");
//                .logoutSuccessUrl("/"); // 로그아웃 성공 화면


//        http.exceptionHandling() // 오류페이지 핸들링
//                .accessDeniedPage("/error/denied");

//        http.authorizeRequests()
//                .antMatchers("/trainer/**")
//                .hasAuthority("ROLE_TRAINER");

        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService)
                .passwordEncoder(getPasswordEncoder());
        super.configure(auth);
    }
}
