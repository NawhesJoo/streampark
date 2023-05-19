package com.project.dto;

import java.util.Date;

import lombok.Data;

// 결제내역
@Data
public class Paychk {
  // 시퀀스
  private Long paychkNo;
  // M or T
  private String type;
  // 아이디
  private String id;
  // 내용
  private String token;
  // 요금제 번호
  private Long grade;
  // 결제일자
  private Date regdate;
}