package com.project.dto;

import java.util.Date;

import lombok.Data;

// 문의 답글
@Data
public class QnaReply {
  // 답글번호
  private Long replyno;
  // 게시글번호
  private Long no;
  // 내용
  private String content;
  // 프로필 번호
  private Long profileno;
  // 작성날짜
  private Date regdate;
}
