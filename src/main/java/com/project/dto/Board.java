package com.project.dto;

import java.util.Date;

import lombok.Data;

// 문의
@Data
public class Board {

  // 게시판번호
  private Long no;
  // 제목
  private String title;
  // 내용
  private String content;
  // 문의글 암호
  private String password;
  // 작성일
  private Date regdate;
  // 프로필 번호
  private Long profileno;
}