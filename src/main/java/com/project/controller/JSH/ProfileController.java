package com.project.controller.JSH;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    final ProfileService profileService;
    final ProfileMapper pMapper;
    final ProfileRepository pRepository;
    final ProfileimgRepository piRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 프로필 선택창
    @GetMapping(value = "/select.do")
    public String selectGET(Model model, HttpSession session){
        // 세션에서 멤버 id를 가져옴
        // String id = (String) session.getAttribute("id");
        String id ="id";
        // 멤버에 연결된 프로필 목록
        List<Profile> profiles = profileService.selectprofile(id);
        model.addAttribute("profiles", profiles);
        // log.info("profileimg", profiles.toString());
        return "/JSH/StreamPark_profile";
    }

    // 프로필 생성
    @GetMapping(value = "/create.do")
    public String createGET(Model model){
        model.addAttribute("profile", new Profile());
        return "/JSH/StreamPark_profile";
    }

    @PostMapping(value = "/create.do")
    public String createPOST(@ModelAttribute("profile") Profile profile,
        // @RequestParam("keyword") String keyword,
        // @RequestParam("id") String id,
        // @RequestParam("nickname") String nickname,
        BindingResult result,
        HttpSession session) {
    // 세션에서 멤버 ID 가져오기
    // String memberId = (String) session.getAttribute("id");
    String memberId = "1";

    // 프로필 정보 설정
    Member member = new Member();
    member.setId(memberId);
    // pMapper.createProfile(keyword, memberId, nickname);
    profile.setMember(member);
    log.info("profile =>", profile);
    // 프로필 저장
    pRepository.save(profile);

    // 프로필 생성 후 리다이렉트할 페이지 지정
    return "redirect:/JSH/StreamPark_profile";
    }

    // 프로필 로그인
    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "/JSH/logintest";
    }

    @PostMapping(value = "/login.do")
    public String loginPOST(@RequestParam("nickname") String nickname,
                            @RequestParam(value = "profilepw", required = false) String profilePw,
                            Model model, HttpSession session) {
        if (profilePw == null || profilePw.isEmpty()) {
            // profilePw 값이 null이거나 빈 문자열인 경우에는 nickname만 입력된 경우이므로 로그인 성공
            session.setAttribute("nickname", nickname); // 세션에 nickname 저장
        } else {
            // profilePw 값이 null이 아니고 비어있지 않은 경우에는 nickname과 profilepw가 모두 입력된 경우이므로 추가적인 로그인 처리를 수행할 수 있음
            // 비밀번호 검증 등의 로직을 수행할 수 있음
        }
        log.info("nickname => {}", nickname.toString());
        log.info("profilePw => {}", profilePw.toString());
        return "redirect:/profile/home.do"; // 로그인 후 이동할 경로
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

    // 닉네임 변경
    @GetMapping(value = "/updatenickname.do")
    // public String updatenicknameGET(HttpSession session) {
    //     String nickname = (String) session.getAttribute("nickname");
    public String updatenicknameGET(){
        return "/JSH/mypage";
    }

    @PostMapping(value = "/updatenickname.do")
    public String updatenicknamePOST(
        @ModelAttribute("profile") Profile profile,
        @RequestParam("newnickname") String newNickname,
        @RequestParam("profilepw") String profilepw,
        Model model) {
    try {
        String currentnickname = "a1";
        Profile profile1 = pRepository.findByNickname(currentnickname);
        profile1.setNickname(newNickname);

        if (bcpe.matches(profilepw, profile1.getProfilepw())) {
            pMapper.updateNickname(profile1.getNickname(), newNickname, profilepw);
        } else {
            return "/JSH/logintest";
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "/JSH/mypage";
    }

    model.addAttribute("profile", profile);
    return "/JSH/hometest";
}
    

    // 프로필 삭제

}