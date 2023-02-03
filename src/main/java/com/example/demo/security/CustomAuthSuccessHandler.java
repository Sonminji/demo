package com.example.demo.security;


import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.json.JSONSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    // 인증 성공 시
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserInfo userInfo = ((UserDetailsDTO) authentication.getPrincipal()).getUserInfo();



        clearAuthenticationAttributes(request);

        if(userInfo.getAuthority().equals("USER")){
            redirectStrategy.sendRedirect(request, response, "/");
        } else{
            redirectStrategy.sendRedirect(request, response, "/user/login");
        }


//        response.setStatus(HttpStatus.OK.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


//        objectMapper.writeValue(response.getWriter(), userInfo);

        // 사용자 정보 조회
//        UserInfo userInfo = ((UserDetailsDTO) authentication.getPrincipal()).getUserInfo();
//
////        response.setStatus(HttpServletResponse.SC_OK);
////        response.sendRedirect("/");
//        HashMap<String, Object> responseMap = new HashMap<>();
//
//        JSONObject jsonObject;
//
//        List<String> roleName = new ArrayList<>();
//
//        authentication.getAuthorities().forEach(auth -> {
//            roleName.add(auth.getAuthority());
//        });
//
//        if(roleName.contains("USER")){
//            responseMap.put("result", "success");
//            responseMap.put("resultCode", 200);
//            responseMap.put("userInfo", userInfo);
//            responseMap.put("failMsg", null);
//            responseMap.put("url", "/");
//        }
//
//        jsonObject = new JSONObject(responseMap);
//
//
//
//
//        // [STEP4] 구성한 응답 값을 전달합니다.
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json");
//        PrintWriter printWriter = response.getWriter();
//        printWriter.print(jsonObject);  // 최종 저장된 '사용자 정보', '사이트 정보' Front 전달
//        printWriter.flush();
//        printWriter.close();
    }

}
