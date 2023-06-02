package com.project.restcontroller.JSH;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.JSH.Login;
import com.project.entity.Profile;
import com.project.repository.ProfileRepository;
import com.project.repository.ProfileimgRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/profile")
@RequiredArgsConstructor
public class RestProfileController {

    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    final ProfileRepository pRepository;
    final ProfileimgRepository piRepository;
    
    // 닉네임 중복확인
    @GetMapping(value = "/nickname.do")
    public Map<String,Object> checkNickname(@RequestParam("nickname") String nickname){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(nickname);
            if(nickname.isEmpty()){
                retMap.put("result",2);
                retMap.put("status", 409);
                retMap.put("message", "닉네임 입력하셈");
            }
            else {
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
        }
        catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // 닉네임만 중복확인
    @GetMapping(value = "/newnickname1.do")
    public Map<String,Object> newnickname1(@RequestParam("newnickname") String newnickname){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(newnickname);
            if(newnickname.isEmpty()){
                retMap.put("result",2);
                retMap.put("status", 409);
                retMap.put("message", "닉네임 입력하셈");
            }
            else {
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
    @RequestParam("nickname") String nickname, @RequestParam("profilepw") String profilepw, Model model){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(nickname);
            Profile newprofile = pRepository.findByNickname(newnickname);
            if(newnickname.isEmpty()){
                retMap.put("status", 409);
                retMap.put("result", 4);
                retMap.put("message", "닉네임 입력하시오");
            }
            else {
                if(model.getAttribute(profilepw) != null){
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
            if(model.getAttribute(profilepw) == null){
                if( newprofile == null ){
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
            }
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
            if(profile.getProfilepw() != null){
                if(bcpe.matches(profilepw, profile.getProfilepw()) == true){
                    // 암호가 일치할 경우
                    retMap.put("result", 1);
                    retMap.put("status", 200);
                } else {
                    // 암호가 일치하지 않을 경우
                    retMap.put("result", 0);
                    retMap.put("status",409);
                }
            } if (profilepw == null){
                    retMap.put("result", 2);
                    retMap.put("status", 409); 
            }
            if(profile.getProfilepw() == null){
                retMap.put("result", 1);
                retMap.put("status", 200);
            }
        }
            catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
            }
            return retMap;
        
    }

    @GetMapping(value = "/imgchk.do")
    public Map<String,Object> imgchk(@RequestParam("chk") String chk){
        Map<String, Object> retMap = new HashMap<>();
        try{
            if(chk.length() > 0){ // 파일 선택 했을 때
                retMap.put("result", 1);
                retMap.put("status", 200);
                retMap.put("message", "파일 선택됨");
            } else { // 선택 안했을 때
                    retMap.put("result", 0);
                    retMap.put("status", 409); 
                    retMap.put("message", "파일 선택 안됨"); 
            }
        }
            catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
            }
            return retMap;
    }

    @PostMapping(value = "/login.do")
    public Map<String,Object> loginPOST(@RequestBody Login obj){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(obj.getNickname());
            if(profile.getProfilepw() == null){
                retMap.put("result", 0);
                retMap.put("status", 409);
            }
            else {
                retMap.put("result", 1);
                retMap.put("status", 200);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }    
    
    // 암호 삭제
    @GetMapping(value = "/deletepw.do")
    public Map<String,Object> deletepw(@RequestParam("nickname1") String nickname,
        @RequestParam("profilepw1") String profilepw){
        Map<String, Object> retMap = new HashMap<>();
        try{
            Profile profile = pRepository.findByNickname(nickname);            
            if(profile.getProfilepw() != null && profile.getProfilepw().length() >= 1){ // profilepw가 있을 때
                if(bcpe.matches(profilepw, profile.getProfilepw())){
                    retMap.put("result", 1);
                    retMap.put("status", 200);
                    retMap.put("message", "삭제");
                } else if(profilepw.isEmpty()){
                    retMap.put("result", 3);
                    retMap.put("status", 409);
                    retMap.put("message", "입력 안함");
                } else {
                    retMap.put("result", 2);
                    retMap.put("status", 409);
                    retMap.put("message", "암호 틀림");
                }
            } else { // profilepw 없을 때
                    retMap.put("result", 0);
                    retMap.put("status", 409); 
                    retMap.put("message", "오류가 발생하였습니다."); 
            }
        }
            catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
            }
            return retMap;
    }
}
