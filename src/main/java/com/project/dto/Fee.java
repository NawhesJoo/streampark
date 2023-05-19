package com.project.dto;

import lombok.Data;

// 멤버십 요금제
@Data
public class Fee {
  // 요금제 번호
  private Long grade;
  // 가격
  private Long price;
  // 이름
  private String name;
  // 수용인원
  private Long capacity;
  // 유효인원
  private String expiration;
}