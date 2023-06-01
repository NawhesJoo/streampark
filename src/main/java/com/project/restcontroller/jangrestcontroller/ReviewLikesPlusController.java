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
import com.project.dto.Reviewreport;
import com.project.mapper.ReviewLikesMapper;
import com.project.mapper.ReviewMapper;
import com.project.mapper.ReviewReportMapper;
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
    final ReviewReportMapper reviewreportMapper;
    final ReviewRepository rRepository;
    final BigInteger profileno = BigInteger.valueOf(93);
    final Long profileno1 = Long.valueOf(93);

    @PutMapping(value="/reportplus.json")
    public Map<String, Object> reportpluscntPUT(@RequestBody Map<String, Object> recvMap) {
        // log.info(format, recvMap.get("no"));
        // log.info(format, recvMap.get("pno"));

        long review_No = Long.valueOf(String.valueOf(recvMap.get("no")));
        long profileno = Long.valueOf(String.valueOf(recvMap.get("pno")));
        Map<String, Object> retMap = new HashMap<>();

        Reviewreport obj = new Reviewreport();
        obj.setReview_No(review_No);
        obj.setProfileno(profileno1);

        int ret2 = reviewreportMapper.insertProfile2(obj); // 싫어요 버튼 눌렀을 때 DB에 등록
        // log.info(format, ret2);

        Reviewreport obj2 = new Reviewreport();
        obj2.setReview_No(review_No);
        obj2.setProfileno(profileno1);
        List<Reviewreport> obj1 = reviewreportMapper.selectReviewreportNo(obj2); // 신고 시퀀스 값 가져오기
        // log.info(format, obj1.toString());

        long reviewreport_No = Long.valueOf(obj1.get(0).getReviewreport_No().toString()); // 시퀀스 값 변수선언
        // log.info(format, reviewreport_No);

        // chkreport 수 조회
        List<Reviewreport> list2 = reviewreportMapper.selectChkreport(reviewreport_No);
        long chkreport = Long.valueOf(list2.get(0).getChkreport().toString());
        // log.info(format, chkreport);
        // db처리
        


        long reportcnt = new BigDecimal(recvMap.get("no").toString()).setScale(0, RoundingMode.FLOOR).longValue();
        Long select1 = reviewMapper.reviewreportselect(reportcnt); // 싫어요 개수 세기
        // log.info(format, select1); // 개수 찍어보기

        if(chkreport == 0) {
            int ret3 = reviewreportMapper.increaseChkreport(reviewreport_No);
            int ret = reviewMapper.reviewreport(recvMap); // 싫어요 증가
            retMap.put("status", ret); // 싫어요 증가에 대한 성공여부 전달
            log.info(format, ret);

        }
        else {
            retMap.put("status", 0);
        }
        if (select1 >= 50) {
            reviewMapper.reviewreportdelete(reportcnt); // 개수에 도달했을 때 자동삭제
        }
        
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
        obj.setReview_No(review_No);
        obj.setProfileno(profileno1);

        Map<String, Object> retMap = new HashMap<>();
        // db처리
        int ret2 = reviewlikesMapper.insertProfile(obj);
        
        log.info(format, ret2);
        // log.info(format, ret1);

        Reviewlikes obj2 = new Reviewlikes();
        obj2.setReview_No(review_No);
        obj2.setProfileno(profileno1);
        List<Reviewlikes> obj1 = reviewlikesMapper.selectReviewlikesNo(obj2);
        log.info(format, obj1.toString());

        long reviewlikes_No = Long.valueOf(obj1.get(0).getReviewlikes_No().toString());
        // log.info(format, reviewlikes_No);

        // chklikes 수 조회
        List<Reviewlikes> list2 = reviewlikesMapper.selectChklikes(reviewlikes_No);
        long chklikes = Long.valueOf(list2.get(0).getChklikes().toString());
        // log.info(format, chklikes);

        // chklikes 값 증감
        if(chklikes == 1) {
            int ret3 = reviewlikesMapper.decreaseChklikes(reviewlikes_No);
            int ret5 = reviewMapper.reviewlikesminus(recvMap);
        }
        else {
            int ret1 = reviewMapper.reviewlikes(recvMap);
            int ret4 = reviewlikesMapper.increaseChklikes(reviewlikes_No);
            // log.info(format, chklikes);
        }


        // retMap.put("status", ret1);
        retMap.put("status1", chklikes);
        // retMap.put("status", ret2);
        
        
        return retMap;
    }
    
}
