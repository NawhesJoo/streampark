package com.project.service.JeongService;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.dto.Jeong.BestList;
import com.project.dto.Jeong.PaymentVideolist;
import com.project.entity.Fee;
import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.entity.Videolist;
import com.project.repository.Projections.MemberProjection;

@Service
@Component
public interface JeongService {
    
    //비디오 코드로 비디오 타이틀, 이미지번호 받아오기
    List<PaymentVideolist> getVideoTitle(List<PaymentVideolist> paymentVideolists);

    //paymentlist -> paymentvideolist 변경 기본키,프로필번호,비디오코드,구매날짜 
    List<PaymentVideolist> paymenstlistToPaymentVideolist(List<com.project.entity.Paymentlist> paymentlists);

    //리뷰 많은 순으로 상위5개
    List<BestList> selectTop5Review();

    // 비디오 코드로 값들 가져오기
    List<Videolist> findTop5Videolist(List<BestList> bestLists);

    //시청 많은 순으로 상위5개
    List<BestList> selectTop5Watchlist();

    //Paychk 등록(멤버쉽)
    int insertPaychkMembership(Paychk obj);
    
    //Paychk 등록(멤버쉽)
    int insertPaychkToken(Paychk obj);

    //멤버쉽 등급 변경(chk)
    int updateMembership(Member member);
    
    //프로필 기본키로 프로필 가져오기
    Profile findProfileById(long profileno);

    //멤버 기본키로 멤버프로젝션 가져오기
    MemberProjection findMemberById(String id);

    //날짜 내림차순으로 한뒤 제일 최신것 가져오기
    Paychk findPaychkTopByRegdate();

    //타입 아이디 검색 후 날짜 내림차순으로 한뒤 제일 최신것 가져오기
    Paychk findPaychkMemberidAndTypeTopByRegdate(String id, String type);

    //모든 요금제 정보 가져오기
    List<Fee> findFeeAll();

    //요금제 등급에 맞는 정보 가져오기
    Fee findFeeById(BigInteger grade);


}
