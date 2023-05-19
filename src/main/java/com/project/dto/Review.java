package com.project.dto;

import java.util.Date;

import lombok.Data;

// 리뷰
@Data
public class Review {
  // 리뷰번호
  private Long reviewNo;
  // 내용
  private String content;
  // 작성일자
  private Date regdate;
  // 신고수
  private Long reportcnt;
  // 시청목록번호
  private Long viewno;
  private Long likes;
}