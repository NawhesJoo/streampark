package com.project.controller.jeong;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.project.entity.Review;
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

    SimpleDateFormat fmt = new SimpleDateFormat("yyyy. MM. dd.");

    @PostMapping(value = "/update.do")
    public String updatePOST(@RequestParam(name = "grade") int grade) {
        log.info("updatepost -> {}", grade);
        String id = (String) httpSession.getAttribute("id");
        Member member = new Member();
        member.setId(id);
        member.setMembershipchk(BigInteger.valueOf(grade));
        int ret = jService.updateMembership(member);
        if (ret == 1) {
            return "redirect:/jeong/index.do";

        }else{
            return "redirect:/jeong/index.do";
        }
    }

    @PostMapping(value = "/pay.do")
    public String payPOST(@RequestParam(name = "membership", required = false, defaultValue = "0") int grade,
            @RequestParam(name = "price") int price,
            @RequestParam(name = "token", required = false, defaultValue = "0") long token) {
        String id = (String) httpSession.getAttribute("id");

        log.info("payPOST -> 아이디 {}, 등급 {}, 가격 {}, 토큰 {} ", id, grade, price, token);
        Paychk obj = new Paychk();
        Fee fee = new Fee();
        Member member = new Member();
        Chargetoken chargetoken = new Chargetoken();
        int ret = 0;
        if (grade != 0) { // 멤버쉽 결제시
            chargetoken.setToken("0"); // 토큰 0 설정
            member.setId(id);
            fee.setGrade(BigInteger.valueOf(grade));
            obj.setFee(fee);
            obj.setMember(member);
            obj.setType("M");
            obj.setChargetoken(chargetoken);
            obj.setPrice(BigInteger.valueOf(price));
            ret = jService.insertPaychkMembership(obj);
        } else if (token != 0) { // 토큰 결제시
            chargetoken.setToken(String.valueOf(token)); // 받아온 토큰 갯수 설정
            member.setId(id);
            fee.setGrade(BigInteger.valueOf(grade)); // 등급 0 설정
            obj.setFee(fee);
            obj.setMember(member);
            obj.setType("T");
            obj.setChargetoken(chargetoken);
            obj.setPrice(BigInteger.valueOf(price));
            ret = jService.insertPaychkToken(obj);
        }

        if (ret == 1) {
            return "redirect:/jeong/index.do";

        } else {
            return "redirect:/jeong/index.do";
        }

    }

    @GetMapping(value = "/paychk.do")
    public String paychkGET(Model model) {
        String id = (String) httpSession.getAttribute("id");
        List<Paychk> plist = payRepository.findByMember_id(id);
        List<Paychk> membershipList = payRepository.findByMember_idAndType(id, "M");
        List<Paychk> tokenList = payRepository.findByMember_idAndType(id, "T");

        // log.info("{}", plist);
        // log.info("{}", membershipList);
        // log.info("{}", tokenList);

        model.addAttribute("plist", plist);
        model.addAttribute("mlist", membershipList);
        model.addAttribute("tlist", tokenList);
        return "/jeong/StreamPark_paychk";
    }

    @GetMapping(value = "/token.do")
    public String tokenGET(Model model) {
        Profile profile = jService.findProfileById(88);
        MemberProjection member = jService.findMemberById(profile.getMember().getId());
        String id = (String) httpSession.getAttribute("id");

        model.addAttribute("id", id);
        model.addAttribute("token", member.getToken());
        return "/jeong/StreamPark_chargetoken";
    }

    @GetMapping(value = "/membership.do")
    public String membershipGET(Model model,
            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {

        Profile profile = jService.findProfileById(88);
        MemberProjection member = jService.findMemberById(profile.getMember().getId());
        // Paychk paychk = jService.findPaychkTopByRegdate();
        Paychk paychk = jService.findPaychkMemberidAndTypeTopByRegdate(profile.getMember().getId(), "M");
        List<Fee> feelist = jService.findFeeAll();
        httpSession.setAttribute("id", member.getId());
        httpSession.setAttribute("nickname", profile.getNickname());
        httpSession.setAttribute("role", "C");

        log.info("{}", feelist);
        // log.info("membershipGET profile -> {}", profile);
        log.info("membershipGET paychk -> {}", paychk);
        // log.info("membershipGET paychk2 -> {}", paychk2);

        if (paychk == null) { // 멤버쉽 결제 내역이 없을때
            model.addAttribute("cal", 0); // cal이 현재 날짜보다 과거면 1 미래면 -1 -> -1이면 유효 1이면 만료
            model.addAttribute("grade", 0);
        } else { // 멤버쉽 결제내역이 있을때
            Date nowDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(paychk.getRegdate());// 등록된 날짜
            cal.add(Calendar.DATE, 30); // 등록된 날짜 + 30일(cal)
            model.addAttribute("expired", fmt.format(cal.getTime()) + " 만료");
            model.addAttribute("cal", nowDate.compareTo(cal.getTime())); // cal이 현재 날짜보다 과거면 1 미래면 -1 -> -1이면 유효 1이면 만료
            log.info("멤버십 구매 날짜 : {} ", paychk.getRegdate().toString());
            model.addAttribute("grade", paychk.getFee().getGrade());
            Fee nowFee = jService.findFeeById(paychk.getFee().getGrade());
            if (menu != 0) {
                if (menu == 1 && (nowDate.compareTo(cal.getTime()) == -1)) {

                    return "redirect:/pay/membership.do";
                }
            }
            model.addAttribute("fee", nowFee);
        }

        model.addAttribute("id", "a1");
        model.addAttribute("feelist", feelist);
        model.addAttribute("nickname", "nicknick");
        model.addAttribute("role", "C");
        model.addAttribute("token", member.getToken());
        model.addAttribute("currentTokens", member.getToken());

        return "/jeong/StreamPark_membership";
    }

}
