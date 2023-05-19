package com.project.dto;

import lombok.Data;

// 토큰 충전
@Data
public class Chargetoken {
  // 내용
  private String token;
  // 가격
  private Long price;
  // 수량
  private Long quantity;
}
