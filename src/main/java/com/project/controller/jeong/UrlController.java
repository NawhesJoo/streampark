package com.project.controller.jeong;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.service.JeongService.EmailService;
import com.project.service.JeongService.RedisSampleService;
import com.project.utils.JwtUtil2;
import com.project.utils.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/url")
public class UrlController {

    final EmailService emailService;
    final RedisSampleService redisSampleService;
    final JwtUtil2 jwtUtil2;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();    
    private final RedisUtil redisUtil;

    @GetMapping(value = "/home.do")
    public String homeGET() {

        return "/jeong/test/urlhome";
    }

    @GetMapping(value = "/convert.do")
    public String convertGET(@RequestParam(value = "url") String urllink) {
        String url = redisUtil.getData(urllink);
        if(url == null){
            log.info("url이상함");
        }else{
        }      
        return "redirect:"+url;
    }

}
