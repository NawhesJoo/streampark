package com.project.service.KDH;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.project.dto.VideolistView;
import com.project.entity.Member;
import com.project.entity.Videolist;
import com.project.repository.KDH.memberRepository;
import com.project.repository.KDH.videolistRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class DHServiceImpl implements DHService{
    final videolistRepository videolistRepository;
    final memberRepository memberRepository;
    //회원의 권한을 받아 검증후 비디오  추가
    @Override
    public void videolistInsert(Member admin, Videolist obj) {
       admin.setRole("a");
       
        // 아이디를 입력받으면 로그인파트랑 연계
        // Member member=memberRepository.findById(admin.getId()).orElse(null);
        //if(member.getRole().equals("a")){
            if(admin.getRole().equals("a")){
           videolistRepository.save(obj);
       }
    }
    //비디오의 이름을 받아서 작품코드를 조회 episode가 1인것조회
    @Override
    public BigInteger selectnofromtitle(String title) {
        
       return videolistRepository.findByTitleAndEpisode(title, BigInteger.valueOf(1)).getVideocode();

    }
    //조회된 비디오 코드로 
    @Override
    public VideolistView selectvideoOne(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectvideoOne'");
    }
    
}
