package com.project.controller.JSH;

import java.math.BigInteger;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            @RequestParam("nickname") String nickname,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        // 세션에서 멤버 ID 가져오기
        // String memberId = (String) session.getAttribute("id");
        String memberId = "1";

        // 프로필 정보 설정
        Member member = new Member();
        member.setId(memberId);
        profile.setMember(member);
        profile.setNickname(nickname);
        log.info("profile =>", profile);
        // 프로필 저장
        pRepository.save(profile);

        // 프로필 생성 후 리다이렉트할 페이지 지정
        return "redirect:/profile/profilelist.do";
        }   // 완료


    // // 닉네임 중복 확인
    // @PostMapping(value = "/checkDuplicate")
    // @ResponseBody
    // public ResponseEntity<String> checkDuplicate(@RequestParam("nickname") String nickname) {
    //     if (pService.countByNickname(nickname) != 0) {
    //         return ResponseEntity.ok("duplicate");
    //     } else {
    //         return ResponseEntity.ok("unique");
    //     }
    // }


    // 프로필 로그인
    @GetMapping(value = "/login.do")
    public String loginGET(@RequestParam(name = "nickname", required = false) String nickname,
        Model model, HttpSession session) {
        model.addAttribute("nickname", nickname);
        Profile profile1 = pRepository.findByNickname(nickname);
        if(profile1.getProfilepw() == null){
            session.setAttribute("nickname", nickname);
            return "redirect:/profile/home.do";
        }
        return "/JSH/logintest";
    }
    

    @PostMapping(value = "/login.do")
        public String loginPOST(@RequestParam("nickname") String nickname,
            @RequestParam(value = "profilepw", required = false) String profilepw, Model model, HttpSession session) {
            Profile profile1 = pRepository.findByNickname(nickname);
            BigInteger profileno = profile1.getProfileno();
            try{
                if (bcpe.matches(profilepw, profile1.getProfilepw())) {
                    pService.loginProfile(nickname, profilepw);
                    session.setAttribute("profileno", profileno);
                    session.setAttribute("nickname", nickname);
                }
                return "redirect:/profile/home.do";
            } catch (Exception e){
                e.printStackTrace();
                return "redirect:profile/login.do";
            }
        }
    


    @GetMapping(value = "/home.do")
        public String showHomePage(HttpSession session) {
            String nickname = (String) session.getAttribute("nickname");
            log.info("nickname => {}",nickname);
            log.info("{}",session.getAttribute("profileno"));
            if (nickname != null) {
                return "/JSH/hometest"; // 로그인된 경우 홈 페이지 경로
            } else {
                return "redirect:/profile/login.do"; // 로그인되지 않은 경우 로그인 폼으로 리다이렉트
            }
        }
}