package com.fittoo.configuration;

import static com.fittoo.common.message.LoginErrorMessage.INVALID_ID_OR_PWD;

import com.fittoo.common.message.LoginErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        log.info("로그인 실패");
        String msg = INVALID_ID_OR_PWD.message();
        if (exception instanceof InternalAuthenticationServiceException) {
            msg = exception.getMessage();
        }
        setUseForward(true);
        setDefaultFailureUrl("/login?error=true");
        request.setAttribute("errorMessage", msg);

        super.onAuthenticationFailure(request, response, exception);
    }
}
