package com.project.dto;

import lombok.Data;

// 배우
@Data
public class Actorsdto {
  // 배우번호(시퀀스)
  private Long actors_No;
  // 배우이름
  private String actors_Name;
}