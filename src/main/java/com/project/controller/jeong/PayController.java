package com.project.controller.jeong;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Chargetoken;
import com.project.entity.Fee;
import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.repository.JeongRepositories.PaychkRepository;
import com.project.repository.JeongRepositories.Projections.MemberProjection;
import com.project.service.JeongService.JeongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/pay")
public class PayController {

    final HttpSession httpSession;
    final JeongService jService;
    final PaychkRepository payRepository;

    @PostMapping(value = "/pay.do")
    public String payPOST(@RequestParam(name = "membership") int grade, @RequestParam(name = "price") int price) {
        String id = (String) httpSession.getAttribute("id");
        log.info("payPOST -> {}, {}, {} ", id, grade, price);
        Paychk obj = new Paychk();
        Fee fee = new Fee();
        Member member = new Member();
        Chargetoken token = new Chargetoken();
        token.setToken("0");
        member.setId(id);
        fee.setGrade(BigInteger.valueOf(grade));
        obj.setFee(fee);
        obj.setMember(member);
        obj.setType("M");
        obj.setChargetoken(token);
        int ret = jService.insertPaychk(obj);
        if (ret == 1) {
            return "redirect:/home/index.do";

        } else {
            return "redirect:/home/index.do";
        }

    }

    @GetMapping(value = "/membership.do")
    public String membershipGET(Model model) {

        Profile profile = jService.findProfileById(88);
        MemberProjection member = jService.findMemberById(profile.getMember().getId());
        Paychk paychk = jService.findPaychkTopByRegdate();

        // log.info("membershipGET profile -> {}", profile);
        log.info("membershipGET paychk -> {}", paychk);

        httpSession.setAttribute("id", member.getId());
        httpSession.setAttribute("nickname", profile.getNickname());
        httpSession.setAttribute("role", "C");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(paychk.getRegdate());// 등록된 날짜
        cal.add(Calendar.DATE, 30); // 등록된 날짜 + 30일(cal)

        log.info("멤버십 구매 날짜 : {} ", paychk.getRegdate().toString());

        model.addAttribute("cal", date.compareTo(cal.getTime())); // cal이 현재 날짜보다 과거면 1 미래면 -1 -> -1이면 유효 1이면 만료
        model.addAttribute("id", "a1");
        model.addAttribute("nickname", "nicknick");
        model.addAttribute("role", "C");
        model.addAttribute("token", member.getToken());
        model.addAttribute("currentTokens", member.getToken());
        model.addAttribute("grade", 1);

        return "/jeong/StreamPark_pay_join";
    }

}
