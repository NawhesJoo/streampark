package com.project.restcontroller.KSH;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Member;
import com.project.repository.KSH.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "api/member")
@RequiredArgsConstructor
public class RestMemberController {
    
    final HttpSession httpSession;
    final MemberRepository mRepository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/idcheck.json")
    public Map<String, Object> idcheckGET(@RequestParam(name = "id") String id){
        Map<String,Object> retMap = new HashMap<>();
        try {
            long ret = mRepository.countById(id);
            retMap.put("status", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }     
    @PostMapping(value = "/login.json")  
    public Map<String, Object> loginJsonGET(
        @RequestBody Member obj1){
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("status", 0);
            try {
                Optional<Member> obj = mRepository.findById(obj1.getId());
                if(bcpe.matches(obj1.getPw(), obj.get().getPw())){
                    retMap.put("status", 1);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
                retMap.put("status", 0);
            }
            return retMap;
    }
}
