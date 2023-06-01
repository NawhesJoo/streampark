package com.project.service.jang;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    // 프로필 번호로 프로필 작성자 이름 가져오기
    public int profilenoToProfilename(long profileno);
    
}
