package com.project.controller.jeong;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.Jeong.Emailtest;
import com.project.service.JeongService.EmailService;
import com.project.service.JeongService.RedisSampleService;
import com.project.utils.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
// @RequiredArgsConstructor
@RequestMapping(value = "/api/test1")
@RequiredArgsConstructor
public class restTest1 {

    final EmailService emailService;    
    final RedisSampleService redisSampleService;
    private final RedisUtil redisUtil;

    @PostMapping("/emailchk.do")
    public Map<String,Object> emailChk(@RequestBody Emailtest test) throws Exception {
        Map<String,Object> retMap = new HashMap<>();
        
        
        log.info("emailchk -> {}", test.getEmail());
        log.info("emailchk -> {}", test.getCode());
        
        String code = redisUtil.getData(test.getEmail());
        
        if(code.equals(test.getCode())){
            retMap.put("status", 200);
        }else{
            retMap.put("status", 0);
        }        

        return retMap;
    }

    @PostMapping("/emailconfirm.do")
    public Map<String,Object> emailConfirm(@RequestBody Emailtest test) throws Exception {
        Map<String,Object> retMap = new HashMap<>();
        String code = emailService.sendSimpleMessage(test.getEmail());
        
        retMap.put("status", 200);
        retMap.put("code", code);           
        log.info("emailconfirm -> {}", test.getEmail());
        log.info("emailconfirm -> {}", code);
        redisUtil.setDataExpire(test.getEmail(), code, 300L);  //300초 동안 유효

        return retMap;
    }

    @PostMapping(value = "/getRedisStringValue")
    public void getRedisStringValue(@RequestParam(value = "key") String key) {
        log.info("{}",key);
        redisSampleService.getRedisStringValue(key);
    }

}
