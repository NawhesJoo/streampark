package com.project.mapper.JSH;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.dto.Profiledto;

@Mapper
public interface ProfileMapper {
    
    @Select ({
        "SELECT * FROM Profile WHERE nickname = #{nickname}"
    })
    public Profiledto findByNickname(String nickname);

    @Update ({
        "UPDATE Profile SET nickname = #{newnickname} WHERE nickname = #{nickname} and profilepw = #{profilepw}"
    })
    public int updateNickname(@Param("nickname") String nickname, @Param("newnickname") String newNickname, @Param("profilepw") String profilepw);

    @Update ({
        "UPDATE Profile SET nickname = #{newnickname} WHERE nickname = #{nickname}"
    })
    public int updateNickname1(@Param("nickname") String nickname, @Param("newnickname") String newNickname);

    @Insert ({
        " INSERT into profile (keyword, id, nickname) values (#{keyword}, #{id}, #{nickname}) "
    })
    public int createProfile(@Param("keyword") String keyword, @Param("id") String id, @Param("nickname") String nickname);

    @Select ({
        "SELECT * FROM Profile WHERE nickname = #{nickname} and profilepw = #{profilepw}"
    })
    public Profiledto loginProfile(@Param("nickname") String nickname, @Param("profilepw") String profilepw);
}