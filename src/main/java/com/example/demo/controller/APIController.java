package com.example.demo.controller;

import com.example.demo.dto.NewsInfoDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@Controller
public class APIController {

    @Autowired
    private APIService apiService;

    @GetMapping("/search")
    public String search(Model model){

        System.out.println("@@@");

        // 동기화
        List<Map<String, Object>> searchContent = apiService.getSearchContent();

//        model.addAttribute("content", searchContent);

        return "/config/news/news.html";
    }

    @GetMapping(value = "/getNewsList", produces = {"application/xml", "application/json"})
    public @ResponseBody List<NewsInfoDTO> getNewsList(){
        return apiService.getNewsList();
    }

}
