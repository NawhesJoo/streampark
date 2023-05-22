package com.project.controller.KSH;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Member;
import com.project.repository.KSH.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class LoginController {
    
    final MemberRepository mRepository;
    final String format = "membercontroller => {}";
    BigInteger token = new BigInteger("0");
    Date date = new Date();
    
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 메인 페이지
    @GetMapping(value = "/main.do")
    public String mainGET(){
        return "/KSH/main";
    }

    // 가입페이지
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/KSH/join";
    }
    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member obj) {
        try {
            obj.setPw(bcpe.encode(obj.getPw()));
            obj.setRole("C");
            obj.setToken(token);
            obj.setRegdate(date);
            log.info(format,obj.toString());

            mRepository.save(obj);
            return "redirect:/member/join.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/join.do";
        }
    }
    
}
