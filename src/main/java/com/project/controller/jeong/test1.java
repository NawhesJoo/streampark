package com.project.controller.jeong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Member;
import com.project.service.JeongService.EmailService;
import com.project.utils.JwtUtil2;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
// @RequiredArgsConstructor
@RequestMapping(value = "/test1")
@RequiredArgsConstructor
public class test1 {

    final EmailService emailService;
    final JwtUtil2 jwtUtil2;

    // final CastsRepository castRepo;
    @GetMapping(value = "/emailtest.do")
    public String emailtestGET() {

        return "jeong/test/emailtest1";
    }

    @GetMapping(value = "/passwordtest.do")
    public String passwordtestGET() {

        return "jeong/test/passwordtest1";
    }

    @GetMapping(value = "/passwordchk.do")
    public String passwordchkGET(Model model, @RequestParam(name = "token") String token) {

        try {
            log.info(token);

            Member obj = jwtUtil2.checkJwt(token);
            if(obj != null){
                model.addAttribute("member", obj);
                model.addAttribute("token", token);
            }else{  
                model.addAttribute("token", "오류");     

            }

        } catch (ExpiredJwtException e1) {
            System.err.println("만료시간 종료1" + e1.getMessage());
        } catch (JwtException e2) {
            System.err.println("토큰오류1" + e2.getMessage());
        } catch (Exception e) {
            System.out.println("e1과 e2 오류 아닌 모든 오류1" + e.getMessage());
        }
        return "jeong/test/passwordtest2";

    }
}
