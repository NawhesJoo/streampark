package com.project.restcontroller.JSH;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Profile;
import com.project.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/profile")
@RequiredArgsConstructor
@Slf4j
public class RestProfileController {

    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    final ProfileRepository pRepository;
    
    // 닉네임 중복확인
    @GetMapping(value = "/nickname.do")
    public Map<String,Object> checkNickname(@RequestParam("nickname") String nickname){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(nickname);
            if(profile == null){
                // 중복되지 않은 경우
                retMap.put("status", 200);
                retMap.put("result", 1);
            } else {
                // 중복된 경우
                retMap.put("status",409);
                retMap.put("result", 0);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // 변경할 닉네임 중복확인
    @GetMapping(value = "/newnickname.do")
    public Map<String,Object> checkNewNickname(@RequestParam("newnickname") String newnickname,
        @RequestParam("nickname") String nickname, @RequestParam("profilepw") String profilepw){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(nickname);
            Profile newprofile = pRepository.findByNickname(newnickname);
            if( newprofile == null && bcpe.matches(profilepw, profile.getProfilepw()) ){
                // 중복되지 않은 경우
                retMap.put("status", 200);
                retMap.put("result", 1);
                retMap.put("message","닉네임 생성 가능");
            }
            else if (newprofile != null){
                // 중복된 경우
                retMap.put("status",409);
                retMap.put("result", 2);
                retMap.put("message", "중복된 닉네임");
            } 
            else if( bcpe.matches(profilepw, profile.getProfilepw()) != true) {
                // 암호가 일치하지 않을 경우
                retMap.put("status",409);
                retMap.put("result", 3);
                retMap.put("message", "암호가 일치하지 않음");
            }
            else {
                retMap.put("status", 409);
                retMap.put("result", 0);
                retMap.put("message","뭐지?");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }    


    // 암호 일치 확인
    @GetMapping(value = "/profilepw.do")
    public Map<String,Object> checkNickname(@RequestParam("nickname") String nickname,
        @RequestParam("profilepw") String profilepw){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(nickname);

            if(bcpe.matches(profilepw, profile.getProfilepw()) == true){
                // 암호가 일치할 경우
                retMap.put("result", 1);
                retMap.put("status", 200);
            } else {
                // 암호가 일치하지 않을 경우
                retMap.put("result", 0);
                retMap.put("status",409);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }


    // @PostMapping(value = "/nickname1.do")
    // public Map<String,Object> checkNickname(@RequestBody Map<String, Object> obj){
    //     Map<String, Object> retMap = new HashMap<>();
    //     try{
    //         log.info("Map => {}", obj.toString());
    //         String id =(String) obj.get("id");
    //         String name = (String) obj.get("name");

    //             // 중복된 경우
    //             retMap.put("status",409);

            
    //     }
    //     catch(Exception e) {
    //         e.printStackTrace();
    //         retMap.put("status", -1);
    //         retMap.put("error", e.getMessage());
    //     }
    //     return retMap;
    // }

    @PostMapping(value = "/nickname2.do")
    public Map<String,Object> checkNickname(@RequestBody Profile obj){
        Map<String, Object> retMap = new HashMap<>();
        try{
            log.info("Map => {}", obj.toString());
            
            
                // 중복된 경우
                retMap.put("status",409);

            
        }
        catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}
