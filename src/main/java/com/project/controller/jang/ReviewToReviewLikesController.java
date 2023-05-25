package com.project.controller.jang;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/reviewlikes")
public class ReviewToReviewLikesController {

    @GetMapping(value = "/test.do")
    public String testGET() {
        try {
            return "/jang/review/reviewlikes";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";        
        }
    }
    
}
