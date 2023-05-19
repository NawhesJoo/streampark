package com.project.mapper.JSH;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.Profileimg;

@Mapper
public interface ProfileimgMapper {
    
    // 이미지 등록
    public int insertProfileimgOne(Profileimg obj);
}
