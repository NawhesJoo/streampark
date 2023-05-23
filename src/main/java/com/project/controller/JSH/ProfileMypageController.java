package com.project.controller.JSH;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Profile;
import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.ProfileRepository;
import com.project.service.JSH.ProfileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class ProfileMypageController {

    final ProfileMapper pMapper;
    final ProfileService pService;
    final ProfileRepository pRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    
    
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
        Profile profile1 = pService.findByNickname(currentnickname);
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
