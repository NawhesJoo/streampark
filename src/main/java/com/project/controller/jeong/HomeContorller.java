package com.project.controller.jeong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/home")
public class HomeContorller {

    @GetMapping(value = "/index.do")
    public String indexGET(){

        return "/jeong/StreamPark_home1.html";
    }
    @GetMapping(value = "/main.do")
    public String mainGET(){
        
        return "/jeong/StreamPark_main.html";
    }
    @GetMapping(value = "/profile.do")
    public String profileGET(){

        return "/jeong/StreamPark_profile.html";
    }

    
}
