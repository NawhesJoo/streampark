package com.project.jangrestcontroller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewLikesPlusController {

    final String format = "reviewRestController => {}";
    final ReviewMapper reviewMapper;
    final BigInteger profileno = BigInteger.valueOf(6);

    @PutMapping(value="/reportplus.json")
    public Map<String, Object> reportpluscntPUT(@RequestBody Map<String, Object> recvMap) {
        log.info(format, recvMap.get("no"));
        log.info(format, recvMap.get("pno"));
        Map<String, Object> retMap = new HashMap<>();
        // db처리
        int ret = reviewMapper.reviewreport(recvMap);


        long reportcnt = Integer.parseInt(String.valueOf(recvMap.get("no")));
        Long select1 = reviewMapper.reviewreportselect(reportcnt);
        log.info(format, select1);
        // if (select1 >= 50) {
        //     reviewMapper.reviewreportdelete(reportcnt);
        // }
        retMap.put("status", ret);
        return retMap;
    }

    @PutMapping(value="/likesplus.json")
    public Map<String, Object> likespluscntPUT(@RequestBody Map<String, Object> recvMap) {
        log.info(format, recvMap.get("no"));
        log.info(format, recvMap.get("pno"));
        Map<String, Object> retMap = new HashMap<>();
        // db처리
        int ret = reviewMapper.reviewlikes(recvMap);
        retMap.put("status", ret);
        return retMap;
    }
    
}
