package com.project.controller.jeong;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Profile;
import com.project.repository.JeongRepositories.Projections.MemberProjection;
import com.project.service.JeongService.JeongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/jeong")
public class HomeContorller {
    final HttpSession httpSession;
    final JeongService jService;

    @GetMapping(value = "/index.do")
    public String indexGET(){
        Profile profile = jService.findProfileById(88);
        MemberProjection member = jService.findMemberById(profile.getMember().getId());
        httpSession.setAttribute("id", member.getId());
        httpSession.setAttribute("nickname", profile.getNickname());
        httpSession.setAttribute("role", "C");

        return "/jeong/StreamPark_home1";
    }
    
    @GetMapping(value = "/main.do")
    public String mainGET(){
        
        return "/jeong/StreamPark_main";
    }

    @GetMapping(value = "/profile.do")
    public String profileGET(){

        return "/jeong/StreamPark_profile";
    }

    
}
