package com.project.jangrestcontroller;

import java.math.BigInteger;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Review;
import com.project.repository.JangRepositories.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewLikesPlusController {

    final String format = "reviewRestController => {}";
    final ReviewRepository rRepository;

    @PutMapping("/likesplus.json")
    public String likespluscntPUT(@RequestParam(name = "review_no") BigInteger review_no, @RequestBody Review review) {
        
        return "/jang/review/selectlist";
    }
    
}
