package com.project.controller.KDH;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.dto.VideolistView;
import com.project.dto.Videolistdto;
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
    public String videoinsertPOST(@ModelAttribute Videolistdto videolist){
        try {
            Member member = new Member(); //멤버를 받기위해 사용 통합후 삭제 및 수정
            member.setRole("a");
            log.info("{}", videolist.toString());
            log.info("{}회", videolist.getEpisode().intValue());
           dhService.videolistInsert(member, videolist);
            return "redirect:/kdh/videoinsert.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }
    @GetMapping(value="/videoupdate.do")
    public String videoupdateGET( @RequestParam(name = "title") String title, Model model){
        try {
            // BigInteger videocode = dhService.selectnofromtitle(title);
            // VideolistView  video=dhService.selectvideoOne(videocode);
            Videolist video = videolistRepository.findByTitle(title);
            model.addAttribute("video", video);
            return  "/KDH/StreamPark_updatevideo";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        } 
    }
    @PostMapping(value = "/videoupdate.do")
    public String videoupdatePOST(@ModelAttribute Videolistdto videolist, @RequestParam(name = "title") String title){
        try {
            Member member = new Member(); //멤버를 받기위해 사용 통합후 삭제 및 수정
            member.setRole("a");
           dhService.videolistUpdate(member, videolist,title);
            return "redirect:/kdh/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }
    @GetMapping(value ="/home.do")
    public String homeGET(Model model){
        List<VideolistView> list=dhService.selectvideolist();
        model.addAttribute("list", list);
        return  "/KDH/StreamPark_home";
    }
    @GetMapping(value="selectone.do")
    public String selectoneGET(@RequestParam(name="title") String title, Model model){
        BigInteger videocode = dhService.selectnofromtitle(title);
        VideolistView  video=dhService.selectvideoOne(videocode);
        model.addAttribute("video", video);
        return "/KDH/StreamPark_selectone";
    }



}
