package com.project.restcontroller.KSH;

import java.util.HashMap;
import java.util.List;
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
import com.project.entity.MemberProjection;
import com.project.repository.MemberRepository;

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
    public Map<String, Object> idcheckGET(@RequestParam(name = "id") String id) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            long ret = mRepository.countById(id);
            retMap.put("status", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

    @GetMapping(value = "/pwcheck.json")
    public Map<String, Object> pwcheckGET(@RequestParam(name = "pw") String pw) {
        String id = "1";
        Map<String, Object> retMap = new HashMap<>();
        try {
            Member obj = mRepository.findById(id).get();
            if(bcpe.matches(pw, obj.getPw())){
                retMap.put("status", 1);
            }
            else{
                retMap.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

    @GetMapping(value = "/emailchk.json")
    public Map<String,Object> emailchkGET(@RequestParam(name = "email") String email){
        Map<String,Object> retMap = new HashMap<>();
        try {
            long ret = mRepository.countByEmail(email);
            retMap.put("status", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status",0);
        }
        return retMap;
    }

    @PostMapping(value = "/login.json")
    public Map<String, Object> loginJsonPOST(
            @RequestBody Member obj1) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("status", 0);
        try {
            Optional<Member> obj = mRepository.findById(obj1.getId());
            if (bcpe.matches(obj1.getPw(), obj.get().getPw())) {
                retMap.put("status", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

    @PostMapping(value = "/findid.json")
    public Map<String, Object> findidPOST(@RequestBody Member obj) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("list", null);
        retMap.put("status", 0);
        try {
            List<MemberProjection> list = mRepository.findByPhoneAndName(obj.getPhone(), obj.getName());
            if (obj.getEmail().equals(list.get(0).getEmail())) {
                retMap.put("list", list);
                retMap.put("status", 1);
            } else if (list.get(0).getEmail() == null || obj.getEmail() != list.get(0).getEmail()) {
                retMap.put("list", null);
                retMap.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("list", null);
            retMap.put("status", 0);
        }
        return retMap;
    }

    @PostMapping(value = "/findpw.json")
    public Map<String, Object> findpwPOST(@RequestBody Member obj) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("status", 0);
        try {
            MemberProjection obj1 = mRepository.findByIdAndName(obj.getId(), obj.getName());
            if (obj1.getEmail().equals(obj.getEmail())) {
                retMap.put("status", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }
}
