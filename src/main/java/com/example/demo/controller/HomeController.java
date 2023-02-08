package com.example.demo.controller;

import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(HttpServletRequest request, Model model, Authentication authentication){
        HttpSession httpSession = request.getSession();
        Object userInfo = httpSession.getAttribute("userInfo");

        if(userInfo != null){
            model.addAttribute("userInfo", userInfo);
        }else{
            model.addAttribute("userInfo", null);
        }

        return "home";
    }

}
