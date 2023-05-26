package com.project.dto;

import java.util.Date;

import lombok.Data;

// 회원
@Data
public class Memberdto {
  // 아이디
  private String id;
  // 암호
  private String pw;
  // 이름
  private String name;
  // 전화번호
  private String phone;
  // 생년월일
  private String birth;
  // 토큰
  private Long token;
  // 성별
  private String gender;
  // 역할
  private String role;
  // 가입날짜
  private Date regdate;
  // 이메일
  private String email;
  // 멤버십 연장 체크
  private Long membershipchk;
}