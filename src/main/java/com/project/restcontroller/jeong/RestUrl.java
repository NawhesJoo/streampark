package com.project.restcontroller.jeong;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.Jeong.Url;
import com.project.service.JeongService.EmailService;
import com.project.service.JeongService.RedisSampleService;
import com.project.utils.JwtUtil2;
import com.project.utils.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/url")
@RequiredArgsConstructor
public class RestUrl {

    final EmailService emailService;
    final RedisSampleService redisSampleService;
    final JwtUtil2 jwtUtil2;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();    
    private final RedisUtil redisUtil;

    @PostMapping(value = "/home.do")
    public String homePOST(@RequestBody Url url){
        log.info("{}",url);
        String tmp = rendomStr();
        log.info(tmp);
        redisUtil.setDataExpire(tmp, url.getLongurl(), 600);
        return "redirect:/url/home.do";
    }

    public static String rendomStr() {
        String rst = "";
        for (int i = 0; i < 8; i++) {
            int rndVal = (int) (Math.random() * 62);
            if (rndVal < 10) {
                rst += rndVal;
            } else if (rndVal > 35) {
                rst += (char) (rndVal + 61);
            } else {
                rst += (char) (rndVal + 55);
            }
        }
        return rst;
    }
    
}
