package com.project.dto;

import java.util.Date;

import lombok.Data;

// 프로필
@Data
public class Profiledto {
  // 프로필 번호
  private Long profileno;
  // 닉네임
  private String nickname;
  // 바꿀 닉네임
  private String newnickname;
  // 아이디
  private String id;
  // 프로필 암호
  private String profilepw;
  // 바꿀 암호
  private String newprofilepw;
  // 등록날짜
  private Date regdate;
  // 0,1
  private Long reviewban;
  // 선호키워드
  private String keyword;
}
