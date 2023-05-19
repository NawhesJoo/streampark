package com.project.dto;

import java.util.Date;

import lombok.Data;

// 문의 답글
@Data
public class QnAreply {
  // 답글번호
  private Long replyno;
  // 내용
  private String content;
  // 작성날짜
  private Date regdate;
  // 게시판번호
  private Long no;
  // 프로필 번호
  private Long profileno;
}