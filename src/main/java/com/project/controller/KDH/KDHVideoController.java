package com.project.controller.KDH;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Member;
import com.project.entity.Videolist;
import com.project.repository.KDH.videolistRepository;
import com.project.service.KDH.DHService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/kdh")
@Slf4j
public class KDHVideoController {
    final videolistRepository videolistRepository;
    final DHService dhService;
    @GetMapping(value = "/error.do")
    public String errorGET(){
        return "/KDH/error";
    }
    @GetMapping(value = "/videoinsert.do")
    public String videoinsertGET(){
        try {
            return  "/KDH/StreamPark_insertvideo";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }
    @PostMapping(value = "/videoinsert.do")
    public String videoinsertPOST(@ModelAttribute Videolist videolist){
        try {
            Member member = new Member(); //멤버를 받기위해 사용 통합후 삭제 및 수정
            member.setRole("a");
            log.info("{}", videolist.toString());
           dhService.videolistInsert(member, videolist);
            return "redirect:/kdh/videoinsert.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }



}
