package com.example.demo.controller;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ErrorResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


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
     * 로그인 페이지
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(Model model){
        return "/config/user/login.html";
    }


    /**
     * 로그인 기능 구현
     * @param id 
     * @return
     */
    @PostMapping(value = "/loginUser")
    public ResponseEntity loginUser(@RequestBody String id, BindingResult bindingResult){

        System.out.println("@@@@");
        System.out.println(id);
        Map<String, Object> resultMap = new HashMap<>();
        UserDetails loginUser = userService.loadUserByUsername(id);

//        resultMap.put("result", "success");
        System.out.println("@@@@2222222");
//        System.out.println();
//        System.out.println(loginUser.getUsername());
        resultMap.put("result", "success");
        return ResponseEntity.ok().body(resultMap);
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

    //모든 사용자 검색
//    @GetMapping("/findAllUser")
//    @ResponseBody
//    public Collection<UserInfoDTO> getInfo(){
//        userService.findAll().forEach(user -> System.out.println(user));
//        return userService.findAll();
//    }

    // 특정 사용자 검색
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
