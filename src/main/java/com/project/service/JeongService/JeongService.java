package com.project.service.JeongService;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.entity.Fee;
import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.repository.Projections.MemberProjection;

@Service
@Component
public interface JeongService {

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

    List<Fee> findFeeAll();

    //요금제 등급에 맞는 정보 가져오기
    Fee findFeeById(BigInteger grade);


}
