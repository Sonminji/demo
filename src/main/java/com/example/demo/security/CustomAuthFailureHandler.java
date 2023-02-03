package com.example.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;

@Slf4j
@Configuration
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();


    // 인증 실패 시
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        
        String failMsg = "";

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Exception 메시지 처리
        if (exception instanceof AuthenticationServiceException) {
            failMsg = "등록되지 않은 사용자입니다.";

        } else if (exception instanceof BadCredentialsException) {
            failMsg = "등록되지 않은 사용자입니다.";

        } else if (exception instanceof LockedException) {
            failMsg = "잠긴 계정입니다.";

        } else if (exception instanceof DisabledException) {
            failMsg = "등록되지 않은 사용자입니다.";

        } else if (exception instanceof AccountExpiredException) {
            failMsg = "아이디를 입력해주세요.";

        } else if (exception instanceof CredentialsExpiredException) {
            failMsg = "비밀번호를 입력해주세요.";
        } else {
            failMsg = exception.getMessage();
        }
        
        
        failMsg = URLEncoder.encode(failMsg, "UTF-8");      // 한글 깨짐 방지
        setDefaultFailureUrl("/user/login?error=true&exception="+failMsg);      // 다음 번에는 errorMsg를 내부로 고려

        super.onAuthenticationFailure(request, response, exception);        // redirect 방지 고려
    }
}
