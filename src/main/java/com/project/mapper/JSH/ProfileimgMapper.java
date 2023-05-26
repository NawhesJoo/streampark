package com.project.mapper.JSH;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileimgMapper {
    
    // 이미지 삭제
    @Delete ({
        "DELETE from Profileimg WHERE profileno = #{profileno}"
    })
    public int deleteProfileimg(BigInteger profileno);
}
