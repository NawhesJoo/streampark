package com.project.controller.KSH;

import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Member;
import com.project.repository.KSH.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    final HttpSession httpSession;
    final MemberRepository mRepository;
    final String format = "membercontroller => {}";
    BigInteger token = new BigInteger("0");
    Date date = new Date();

    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 메인 페이지
    @GetMapping(value = "/main.do")
    public String mainGET() {
        return "/KSH/main";
    }

    // 가입페이지
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/KSH/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member obj) {
        try {
            obj.setPw(bcpe.encode(obj.getPw()));
            obj.setRole("C");
            obj.setToken(token);
            obj.setRegdate(date);
            log.info(format, obj.toString());

            mRepository.save(obj);
            return "redirect:/member/wellcome.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/join.do";
        }
    }

    // 웰컴 페이지
    @GetMapping(value = "/wellcome.do")
    public String wellcomeGET() {
        return "KSH/wellcome";
    }

    // 로그인 페이지
    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "KSH/login";
    }

    @PostMapping(value = "/login.do")
    public String loginPOST(@ModelAttribute Member obj, Model model) {
        try {
            Member obj1 = mRepository.findById(obj.getId()).orElse(null);
            if (bcpe.matches(obj.getPw(), obj1.getPw())) {
                httpSession.setAttribute("id", obj.getId());
                httpSession.setAttribute("role", obj.getRole());
                return "redirect:/Profile/create.do";
            } else {
                return "redirect:/member/login.do";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/login.do";
        }
    }

    // 아이디 찾기
    @GetMapping(value = "/findid.do")
    public String findIdGET(){
        return "KSH/findid";
    }

    @PostMapping(value = "/findid.do")
    public String findIdPOST(){
        return "KSH/findid";
    }
    // 비밀번호 찾기
    @GetMapping(value="/findpw.do")
    public String findPwGET() {
        return "KSH/findPw";
    }
    

}
