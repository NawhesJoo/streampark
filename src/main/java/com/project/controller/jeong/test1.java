package com.project.controller.jeong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.service.JeongService.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
// @RequiredArgsConstructor
@RequestMapping(value = "/test1")
@RequiredArgsConstructor
public class test1 {

    final EmailService emailService;    


    // final CastsRepository castRepo;
    @GetMapping(value = "/emailtest.do")
    public String emailtestGET() {

        return "jeong/test/test1";
    }

    
}
