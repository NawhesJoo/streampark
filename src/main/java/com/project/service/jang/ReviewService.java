package com.project.service.jang;

import org.springframework.stereotype.Service;

import com.project.entity.Profile;

@Service
public interface ReviewService {

    // 프로필 번호로 프로필 작성자 이름 가져오기
    Profile findProfilenoById(long profileno);
    
}
