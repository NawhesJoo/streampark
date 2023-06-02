package com.project.dto;

import lombok.Data;

// 나중에 볼 영상
@Data
public class Interestlistdto {
  // 관심목록 번호
  private Long interestno;
  // 작품코드
  private Long videocode;
  // 프로필 번호
  private Long profileno;
}
