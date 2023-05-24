package com.project.restcontroller.JSH;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Profile;
import com.project.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/profile")
@RequiredArgsConstructor
public class RestProfileController {

    final ProfileRepository pRepository;
    
    @GetMapping(value = "/nickname.do")
    public Map<String,Object> checkNickname(@RequestParam("nickname") String nickname){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(nickname);
            if(profile == null){
            // 중복되지 않은 경우
            retMap.put("status", 200);
            retMap.put("message", "사용할 수 있는 닉네임입니다.");
        } else {
            // 중복된 경우
            retMap.put("status",409);
            retMap.put("message", "중복된 닉네임입니다.");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }
}
