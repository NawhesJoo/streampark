package com.project.dto;

import java.util.Date;

import lombok.Data;

@Data
public class VideolistView {
   
    private Long videocode;
    // 작품 제목
    private String title;
    // 키워드
    private String keyword;
    // 감독
    private String pd;
    //배우목록
    private String castactor;
    // 연령제한
    private String chkage;
    // 개봉일자
    private String opendate;
    // 필요 토큰
    private Long price;
    // 회차 수
    private Long episode;
    // 등록일자
    private Date regdate;
    
    private long imageNo; // 대표 이미지 번호를 저장할 임시변수

    }

