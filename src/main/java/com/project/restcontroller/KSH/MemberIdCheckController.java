package com.project.restcontroller.KSH;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.repository.KSH.MemberRepository;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(value = "api/member")
@RequiredArgsConstructor
public class MemberIdCheckController {
    
    final MemberRepository mRepository;

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
}
