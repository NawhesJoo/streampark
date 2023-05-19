package com.project.dto;

import java.util.Date;

import lombok.Data;

// 구매내역
@Data
public class Paymentlist {
  private Long paymentlistno;
  // 프로필 번호
  private Long profileno;
  // 작품코드
  private Long videocode;
  // 결제날짜
  private Date regdate;
}
