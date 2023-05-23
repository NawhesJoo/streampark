package com.project.restcontroller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RestController
@RequestMapping(value = "/qna")
@RequiredArgsConstructor
public class RestQnAController {
    final String format = "password => {}";

    // Ajax 요청을 처리할 메소드
    @PostMapping("/delete.do")
    public String checkPassword(@RequestParam String password) {       
        log.info("format", password);
        return "redirect:/qna/selectlist.do";
    }
}