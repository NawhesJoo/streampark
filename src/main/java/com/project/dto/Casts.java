package com.project.dto;

import lombok.Data;

// 출연진
@Data
public class Casts {
  // 출연진번호(시퀀스)
  private Long castsNo;
  // 작품코드
  private Long videocode;
  // 배우번호(시퀀스)
  private Long actorsNo;
}
