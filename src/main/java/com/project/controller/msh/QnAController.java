package com.project.controller.msh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/qna")
public class QnAController {

    @GetMapping(value="/selectlist.do")
    public String selectlistGET() {
        return "/msh/selectlist";
    }
    
}
