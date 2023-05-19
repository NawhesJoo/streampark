package com.project.service.KDH;

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
    //회원의 권한을 받아 검증후 비디오 이미지 추가
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
    @Override
    public Long selectnofromtitle(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectnofromtitle'");
    }
    @Override
    public VideolistView selectvideoOne(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectvideoOne'");
    }
    
}
