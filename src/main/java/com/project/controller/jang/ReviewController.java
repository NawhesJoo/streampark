package com.project.controller.jang;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/review")
@RequiredArgsConstructor
public class ReviewController {

    final String format = "ReviewController => {}";

    @PostMapping(value = "/insert.do")
    public String insertPOST() {
        try {
            
            return "redirect:/review/insert.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/insert.do")
    public String insertGET(@RequestParam(name = "profileno", required = false, defaultValue = "0") BigInteger profileno, @RequestParam(name = "viewno", required = false, defaultValue = "0") Long viewno, Model model) {
        try {
            log.info(format, profileno);
            log.info(format, viewno);
            model.addAttribute("obj", profileno);
            model.addAttribute("obj1", viewno);
            return "jang/review/insert";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}
