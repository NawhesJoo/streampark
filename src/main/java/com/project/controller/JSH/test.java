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
@RequestMapping(value = "/test")
@RequiredArgsConstructor
public class test {

    final String format = "ProfileContrller => {}";
    final ProfileRepository pRepository;
    
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/JSH/StreamPark_profile";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Profile profile, HttpSession session){
        log.info(format, profile.toString());

        // 세션에서 현재 사용자의 ID 가져오기
        String memberId = (String) session.getAttribute("id");
        // profile.setId(memberId);

        pRepository.save(profile);
        return "redirect:/StreamPark_profile";
    }
}