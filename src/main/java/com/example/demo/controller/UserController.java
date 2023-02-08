package com.example.demo.controller;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ErrorResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private String CLIENT_ID = "9R3dRrJDnRVMPDuGASCs";


    /**
     * 회원가입 페이지
     * @param model
     * @return
     */
    @GetMapping("/join")
    public String join(Model model){
        UserInfoDTO userInfo = new UserInfoDTO();
        model.addAttribute("UserInfo",userInfo);
        return "/config/user/join.html";
    }

    /**
     * 회원가입 기능 구현
     * @param userInfoDTO
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/joinUser")
    @ResponseBody
    public ResponseEntity joinUser(@RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult){
        Map<String, Object> resultMap = new HashMap<>();
        String status = userService.save(userInfoDTO);

        resultMap.put("result", "success");

        return ResponseEntity.ok().body(resultMap);
    }

    /**
     * 카카오 로그인
     * @param request
     * @param userInfoDTO
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/smsJoin")
    public String smsJoin(HttpServletRequest request, UserInfoDTO userInfoDTO, BindingResult bindingResult){
        HttpSession httpSession = request.getSession();

        UserInfoDTO userInfo = userService.loginWithSMS(userInfoDTO);

        httpSession.setAttribute("userInfo", (userInfo != null ? userInfo : ""));

        return "redirect:/";
    }

    /**
     * 로그인 페이지
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/config/user/login.html";
    }


    /**
     * 로그인 기능 구현
     * @param userInfoDTO
     * @return
     */
    @PostMapping(value = "/loginUser")
    @ResponseBody
    public ResponseEntity loginUser(Model model, @RequestBody UserInfoDTO userInfoDTO, BindingResult bindingResult){

        Map<String, Object> resultMap = new HashMap<>();
        UserDetails loginUser = userService.loadUserByUsername((String) userInfoDTO.getId());

        resultMap.put("result", "success");

        return ResponseEntity.ok().body(resultMap);
    }



    //모든 사용자 검색
//    @GetMapping("/findAllUser")
//    @ResponseBody
//    public Collection<UserInfoDTO> getInfo(){
//        userService.findAll().forEach(user -> System.out.println(user));
//        return userService.findAll();
//    }


    /**
     * 중복 아이디 체크
     * @param id 
     * @return
     */
    @GetMapping("/checkDupli/{id}")
    @ResponseBody
    public boolean checkDupli(@PathVariable String id){
        boolean checkDupli = userService.findByUserId(id);
        return checkDupli;
    }

//
//    //특정 사용자 삭제
//    @GetMapping("/delete/{seq}")
//    public String delete(@PathVariable long seq){
//        return userService.deleteBySeq(seq);
//    }


}
