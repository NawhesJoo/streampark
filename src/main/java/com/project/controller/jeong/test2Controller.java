package com.project.controller.jeong;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Profile;
import com.project.repository.JeongRepositories.MemberRepository;
import com.project.repository.JeongRepositories.ProfileRepository;

import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

@Controller
// @Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/test2")
public class test2Controller {
    
    final MemberRepository mRepository;
    final ProfileRepository pRepository;

    @GetMapping(value = "test.do")
    public String testGET(){
        try {
            // Member test =  mRepository.findById("id1").orElse(null);
            // Profile test = pRepository.findById(BigInteger.valueOf(31)).orElse(null);
            // log.info("test1 -> {}", test);
            // mRepository.deleteById("id1");
                        
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "/jeong/test/index.html";
    }
}
