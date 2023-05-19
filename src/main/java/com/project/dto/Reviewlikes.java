package com.project.dto;

import lombok.Data;

// 리뷰 좋아요수
@Data
public class Reviewlikes {
  // 시퀀스
  private Long reviewlikesNo;
  // 프로필번호
  private Long profileno;
  private Long chklikes;
  // 리뷰번호
  private Long reviewNo;
}