package com.project.dto;

import java.util.Date;

import lombok.Data;

// 프로필 이미지
@Data
public class Profileimg {
  private Long no;
  private String filename;

  private byte[] filedata;
  
  private String filetype;
  private Long filesize;
  private Date regdate;
  // 프로필 번호
  private Long profileno;
}
