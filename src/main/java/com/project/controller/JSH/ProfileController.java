package com.project.controller.JSH;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {

    final String format = "ProfileContrller => {}";
    
    @GetMapping(value = "join.do")
    public String joinGET(){
        return "StreamPark_profile";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Profile profile){
        log.info(format, profile.toString());
        
}
