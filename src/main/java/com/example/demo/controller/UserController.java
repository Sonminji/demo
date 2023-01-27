package com.example.demo.controller;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/join")
    public String join(Model model){
        UserInfo userInfo = new UserInfo();
        model.addAttribute("UserInfo",userInfo);
        return "/config/user/join.html";
    }


    //회원가입
    @PostMapping(value = "/joinUser")
    public String joinUser(@RequestBody UserInfo userInfo) throws Exception {

        Object obj = userInfo;
        for(Field field : obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Object value = field.get(obj);
            System.out.println("field : "+field.getName()+" | value : " + value);
        }

        return userService.save(userInfo);
    }

    //모든 사용자 검색
    @GetMapping("/findAllUser")
    @ResponseBody
    public Collection<UserInfo> getInfo(){
        userService.findAll().forEach(user -> System.out.println(user));
        return userService.findAll();
    }

    // 특정 사용자 검색
    @GetMapping("/search/{id}")
    public Optional<UserInfo> search(@PathVariable String id){
        return userService.findByUserId(id);
    }

    //특정 사용자 삭제
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable long seq){
        return userService.deleteBySeq(seq);
    }


}
