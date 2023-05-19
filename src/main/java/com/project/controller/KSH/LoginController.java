package com.project.controller.KSH;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class LoginController {
    
    @GetMapping(value = "/main.do")
    public String mainGET(){
        return "/KSH/main";
    }
    @GetMapping(value = "login.do")
    public String loginGET(){
        return "/KSH/login";
    }
}
