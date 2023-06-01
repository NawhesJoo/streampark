package com.project.service.jang;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {@Override
    public int profilenoToProfilename(long profileno) {
        try {
            return 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
