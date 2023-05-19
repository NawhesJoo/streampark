package com.project.dto;

import java.util.Date;

import lombok.Data;

// 비디오 이미지
@Data
public class Videoimg {
  private Long no;
  private String filename;
  private byte[] filedata;
  private String filetype;
  private Long filesize;
  private Date regdate;
  // 작품코드
  private Long videocode;
}

