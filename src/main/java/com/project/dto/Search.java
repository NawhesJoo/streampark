package com.project.dto;

import lombok.Data;

@Data
public class Search {
    final String[] typeCode = {"title", "pd", "chkage"};
    final String[] typeName = {"제목", "pd", "시청 가능 연령"};

    private int page = 1;
    private String type = "title";
    private String text = "";
    
}
