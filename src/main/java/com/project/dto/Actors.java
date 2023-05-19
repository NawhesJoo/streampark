package com.project.dto;

import lombok.Data;

// 배우
@Data
public class Actors {
  // 배우번호(시퀀스)
  private Long actorsNo;
  // 배우이름
  private String actorsName;
}