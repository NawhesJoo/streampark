package com.project.controller.KDH;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Videolist;
import com.project.repository.KDH.videolistRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/kdh")
public class KDHVideoController {
    final videolistRepository videolistRepository;
    @GetMapping(value = "/videoinsert.do")
    public String videoinsertGET(){
        try {
            return  "/KDH/StreamPark_insertvideo";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/videoinsert.do")
    public String videoinsertPOST(@ModelAttribute Videolist videolist){
        try {
            videolistRepository.save(videolist);
            return "redirect:/videoinsert.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}
