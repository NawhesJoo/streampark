package com.project.controller.jeong;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.dto.Videolistdto;
import com.project.dto.Jeong.BestList;
import com.project.entity.Profile;
import com.project.entity.Videolist;
import com.project.repository.Projections.MemberProjection;
import com.project.service.JeongService.JeongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/best")
public class bestContorller {
    final HttpSession httpSession;
    final JeongService jService;

    @GetMapping(value = "/best.do")
    public String bestGET(Model model) {
        List<BestList> topWatchList = jService.selectTop5Watchlist();
        List<BestList> topReviewList = jService.selectTop5Review();
        // log.info("bestlist -> {}", topList);
        List<Videolist> videolists = jService.findTop5Videolist(topWatchList);
        List<Videolist> videolists2 = jService.findTop5Videolist(topReviewList);
        // log.info("videolists -> {}", videolists);
        model.addAttribute("watchlists", videolists);
        model.addAttribute("reviewlists", videolists2);

        return "/jeong/StreamPark_best";
    }

    @GetMapping(value = "/index.do")
    public String indexGET() {
        Profile profile = jService.findProfileById(87);
        MemberProjection member = jService.findMemberById(profile.getMember().getId());
        httpSession.setAttribute("id", member.getId());
        httpSession.setAttribute("nickname", profile.getNickname());
        httpSession.setAttribute("role", "C");

        return "/jeong/StreamPark_home1";
    }

    @GetMapping(value = "/main.do")
    public String mainGET() {

        return "/jeong/StreamPark_main";
    }

    @GetMapping(value = "/profile.do")
    public String profileGET() {

        return "/jeong/StreamPark_profile";
    }

}
