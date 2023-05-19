package com.project.dto;

import java.util.Date;

import lombok.Data;

// 시청목록
@Data
public class Watchlist {
  // 시청목록번호
  private Long viewno;
  // 시청 날짜
  private Date viewdate;
  // 작품코드
  private Long videocode;
  // 프로필 번호
  private Long profileno;
}
