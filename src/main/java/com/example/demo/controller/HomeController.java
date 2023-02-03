package com.example.demo.controller;

import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication){

            model.addAttribute("userID", authentication != null ? authentication.getName() : "");

        return "home";
    }

}
