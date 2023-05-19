package com.project.controller.jeong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
// @Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/test2")
public class test2Controller {
    
    @GetMapping(value = "test.do")
    public String testGET(){
        return "/jeong/test/index.html";
    }
}
