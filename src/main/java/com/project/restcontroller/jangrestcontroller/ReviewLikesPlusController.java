package com.project.restcontroller.jangrestcontroller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.Reviewlikes;
import com.project.mapper.ReviewLikesMapper;
import com.project.mapper.ReviewMapper;
import com.project.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewLikesPlusController {

    final String format = "reviewRestController => {}";
    final ReviewMapper reviewMapper;
    final ReviewLikesMapper reviewlikesMapper;
    final ReviewRepository rRepository;
    final BigInteger profileno = BigInteger.valueOf(6);
    final Long profileno1 = Long.valueOf(6);


    @PutMapping(value="/reportplus.json")
    public Map<String, Object> reportpluscntPUT(@RequestBody Map<String, Object> recvMap) {
        log.info(format, recvMap.get("no"));
        log.info(format, recvMap.get("pno"));
        Map<String, Object> retMap = new HashMap<>();
        // db처리
        int ret = reviewMapper.reviewreport(recvMap);


        long reportcnt = new BigDecimal(recvMap.get("no").toString()).setScale(0, RoundingMode.FLOOR).longValue();
        Long select1 = reviewMapper.reviewreportselect(reportcnt);
        log.info(format, select1);
        if (select1 >= 50) {
            reviewMapper.reviewreportdelete(reportcnt);
        }
        retMap.put("status", ret);
        return retMap;
    }

    @PutMapping(value="/likesplus.json")
    public Map<String, Object> likespluscntPUT(@RequestBody Map<String, Object> recvMap) {
        // log.info(format, recvMap.get("no"));
        // log.info(format, recvMap.get("pno"));

        long review_No = Long.valueOf(String.valueOf(recvMap.get("no")));
        long profileno = Long.valueOf(String.valueOf(recvMap.get("pno")));
        // log.info(format, review_No);
        // log.info(format, profileno);
        Reviewlikes obj = new Reviewlikes();
        obj.setReviewNo(review_No);
        obj.setProfileno(profileno1);

        Map<String, Object> retMap = new HashMap<>();
        // db처리
        int ret2 = reviewlikesMapper.insertProfile(obj);
        if(ret2 == 1) {
            int ret1 = reviewMapper.reviewlikes(recvMap);
        }
        // log.info(format, ret2);

        List<Reviewlikes> list = reviewlikesMapper.selectReviewlikesNo();
        log.info(format, list);


        // retMap.put("status", ret);
        // retMap.put("status", ret2);
        return retMap;
    }
    
}
