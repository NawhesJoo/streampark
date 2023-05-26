package com.project.dto;

import java.util.Date;

import lombok.Data;

// 작품목록
@Data
public class Videolistdto {
  // 작품코드
  private Long videocode;
  // 작품 제목
  private String title;
  // 키워드
  private String keyword;
  // 감독
  private String pd;
  // 연령제한
  private String chkage;
  // 등록일자
  private Date regdate;
  // 개봉일자
  private String opendate;
  // 필요 토큰
  private Long price;
  // 회차 수
  private Long episode;
  // 유튜브링크
  private String linkurl;


  private Long imgno;
}