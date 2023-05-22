package com.project.mapper.KDH;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.dto.VideolistView;
import com.project.dto.Videolistdto;

@Mapper
public interface KDHMapper {
    
    @Select({
		"  SELECT v.* FROM VideolistView v  WHERE Videocode=#{videocode}  "
	})
	public VideolistView selectVideoOne(@Param("videocode") BigInteger videocode); 

    //관리자용 영상 수정
	@Update({
		"  UPDATE videolist SET title=#{obj.title}, keyword= #{obj.keyword}, pd=#{obj.pd}, chkage=#{obj.chkage}, opendate=#{obj.opendate} ,price=#{obj.price}, linkurl=#{obj.linkurl}  ",
	    "  WHERE  title=#{nowtitle}  "
	})
    public int videolistUpdate(@Param("obj")Videolistdto obj, String nowtitle);
    

    @Insert({
		"   INSERT INTO videolist (title,keyword,pd,chkage,opendate,price, episode, linkurl)  ",
		"  VALUES(#{obj.title}, #{obj.keyword}, #{obj.pd}, #{obj.chkage}, #{obj.opendate}, #{obj.price}, #{obj.episode}, #{obj.linkurl})  "
	})
	public int videolistInsert(@Param("obj") Videolistdto obj);

    @Select({
		"  SELECT  * FROM VideolistView WHERE episode= 1   "
	})
	public List<VideolistView> selectvideolist();
}
