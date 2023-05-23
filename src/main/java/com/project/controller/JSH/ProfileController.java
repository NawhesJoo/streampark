package com.project.controller.JSH;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Member;
import com.project.entity.Profile;
import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.ProfileRepository;
import com.project.repository.ProfileimgRepository;
import com.project.service.JSH.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {

    final String format = "ProfileContrller => {}";
    final ProfileService pService;
    final ProfileMapper pMapper;
    final ProfileRepository pRepository;
    final ProfileimgRepository piRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    // 프로필 선택창
    @GetMapping(value = "/profilelist.do")
    public String profilelistGET(Model model, HttpSession session){
        try{
            // String id = (String) session.getAttribute("id");
            String id = "1";
            List<Profile> list = pService.selectprofile(id);
            model.addAttribute("list", list);
            log.info("list => {}", list.toString());
            return "/JSH/list";
        }
        catch(Exception e){
            e.printStackTrace();
            return "/JSH/list";
        }
    }


    // 프로필 생성
    @GetMapping(value = "/create.do")
    public String createGET(Model model){
        model.addAttribute("profile", new Profile());
        return "/JSH/create";
    }

    @PostMapping(value = "/create.do")
    public String createPOST(@ModelAttribute("profile") Profile profile,
        HttpSession session) {
    // 세션에서 멤버 ID 가져오기
    // String memberId = (String) session.getAttribute("id");
    String memberId = "12";

    // 프로필 정보 설정
    Member member = new Member();
    member.setId(memberId);
    profile.setMember(member);
    log.info("profile =>", profile);
    // 프로필 저장
    pRepository.save(profile);

    // 프로필 생성 후 리다이렉트할 페이지 지정
    return "/JSH/hometest";
    }   // 완료

    // 프로필 로그인
    @GetMapping(value = "/login.do")
    public String loginGET(Model model, @RequestParam(name = "profileno") BigDecimal profileno,
        @RequestParam(name = "nickname", required = false) String nickname) {
        model.addAttribute("profileno", profileno);
        model.addAttribute("nickname", nickname);
        return "/JSH/logintest";
    }
    

    @PostMapping(value = "/login.do")
    public String loginPOST(@RequestParam("nickname") String nickname,
            @RequestParam(value = "profilepw", required = false) String profilePw, Model model, HttpSession session) {
        session.getAttribute(nickname);
        if (profilePw == null || profilePw.isEmpty()) {
            session.setAttribute("nickname", nickname);
            return "redirect:/profile/home.do";
        } else {
            pService.loginProfile(nickname, profilePw);
            session.setAttribute("nickname", nickname);
        }
        log.info("nickname => {}", nickname.toString());
        log.info("profilePw => {}", profilePw.toString());
        return "/profile/home.do";
    }

    @GetMapping(value = "/home.do")
    public String showHomePage(HttpSession session) {
        String nickname = (String) session.getAttribute("nickname");
        if (nickname != null) {
            return "/JSH/hometest"; // 로그인된 경우 홈 페이지 경로
        } else {
            return "redirect:/profile/login.do"; // 로그인되지 않은 경우 로그인 폼으로 리다이렉트
        }
    }
}