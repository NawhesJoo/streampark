package com.project.controller.JSH;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Profile;
import com.project.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {

    final String format = "ProfileContrller => {}";
    final ProfileRepository pRepository;
    
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/JSH/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(HttpSession session, @ModelAttribute Profile profile) {
        // 세션에서 아이디 가져오기
        String memberId = (String) session.getAttribute("memberId");
        
        // 직접 입력한 아이디, 닉네임, 키워드로 프로필 생성
        Profile newProfile = new Profile();
        newProfile.setMemberId(memberId);
        newProfile.setNickname(profile.getNickname());
        newProfile.setKeyword(profile.getKeyword());

        log.info(format, newProfile.toString());
        pRepository.save(newProfile);

        return "redirect:/StreamPark_profile";
    }
}
