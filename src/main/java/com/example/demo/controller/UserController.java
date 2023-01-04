package com.example.demo.controller;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //회원가입
    @GetMapping("/join")
    public String create(UserInfo userInfo){
        userInfo.setId("aa");
        userInfo.setPassword("bbb111");
        userInfo.setName("name!");
        return userService.save(userInfo);
    }

    //모든사용자검색
    @GetMapping("/findAllUser")
    public Collection<UserInfo> getInfo(){
        userService.findAll().forEach(user -> System.out.println(user));
        return userService.findAll();
    }

    @GetMapping("/search/{id}")
    public Optional<UserInfo> search(@PathVariable String id){
        return userService.findByUserId(id);
    }

    //특정 사용자 삭제
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable long seq){
        return userService.deleteBySeq(seq);
    }
//
//    @GetMapping("/user/{id}")
//    public String read(@PathVariable long id){
//        Optional<User> userOptional = userRepository.findById(id);
//        userOptional.ifPresent(System.out::println);
//
//        return "Create UserInfo";
//    }

}
