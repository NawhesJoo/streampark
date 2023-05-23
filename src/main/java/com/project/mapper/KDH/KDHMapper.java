package com.project.mapper.KDH;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.dto.Actorsdto;
import com.project.dto.VideolistView;
import com.project.dto.Videolistdto;

@Mapper
public interface KDHMapper {
    
    @Select({
		"  SELECT v.* FROM VideolistView v  WHERE v.videocode=#{videocode}  "
	})
	public VideolistView selectVideoOne(@Param("videocode") BigInteger videocode); 

    //관리자용 영상 수정
	@Update({
		"  UPDATE videolist SET title=#{obj.title}, keyword= #{obj.keyword}, pd=#{obj.pd}, chkage=#{obj.chkage}, opendate=#{obj.opendate} ,price=#{obj.price}, linkurl=#{obj.linkurl}  ",
	    "  WHERE  title=#{nowtitle}  "
	})
    public int videolistUpdate(@Param("obj")Videolistdto obj, @Param("nowtitle") String nowtitle);
    

    @Insert({
		"   INSERT INTO videolist (title,keyword,pd,chkage,opendate,price, episode, linkurl)  ",
		"  VALUES(#{obj.title}, #{obj.keyword}, #{obj.pd}, #{obj.chkage}, #{obj.opendate}, #{obj.price}, #{obj.episode}, #{obj.linkurl})  "
	})
	public int videolistInsert(@Param("obj") Videolistdto obj);

    @Select({
		"  SELECT  * FROM Videolist WHERE episode= 1   "
	})
	public List<VideolistView> selectvideolist();
    //casts에 비디오코드로 배우 추가
    @Insert({
		"  INSERT INTO casts (videocode, actors_no)  ",
		"  VALUES(#{videocode}, #{actors_no})  "
	})
    public int castsInsertactor(@Param("videocode") Long videocode, @Param("actors_no") Long actors_no);

    //배우목록 출력
	@Select({
		"  SELECT * FROM ACTORS  "
	})
	public List<Actorsdto> selectActors();

    //현재 작품에 등록된 배우코드 출력
	@Select({
		"  SELECT actors_no FROM casts WHERE videocode=#{videocode}  "
	})
	public List<Long> selectActorsinvideo(@Param("videocode") BigInteger videocode);

    //배우코드로 배우 출력
	@Select({
		"  SELECT actors_no, actors_name FROM actors WHERE actors_no =#{code}"
	})
	public Actorsdto selectnotoname(@Param("code") Long code);

    //현재등록된 배우 중복확인
	@Select({
		"  SELECT COUNT(*) cnt FROM casts WHERE videocode=#{videocode} AND actors_no=#{actors_no} "
	})
	public int  castsInsertactorchk(@Param("actors_no") Long actors_no, @Param("videocode") Long videocode  );

    //casts에 배우삭제
	@Delete({
		"  DELETE FROM casts WHERE videocode=#{videocode} AND actors_no=#{actors_no}  "
	})
	public int castsDeleteactor(@Param("videocode") Long videocode, @Param("actors_no") Long actors_no);

    	//actors에 배우가 없으면 추가
	@Insert({
		"  INSERT INTO actors (actors_name)  ",
		"  VALUES(#{actors_name})  "
	})
	public int actorInsert(@Param("actors_name") String actors_name);

//     //전체 영상 조회
// 	@Select({
// 		"  SELECT  * FROM VideolistView WHERE episode= 1   "
// 	})
// 	public List<VideolistView> selectvideolist();
// 	//영상코드로 영상 조회
// 		@Select({
// 			"  SELECT  * FROM VideolistView WHERE episode= 1 AND title =#{title}  "
// 		})
// 		public VideolistView selectvideoOne(@Param("title") String title);
// 	//관리자용 영상 등록 
// 	@Insert({
// 		"   INSERT INTO videolist (title,keyword,pd,chkage,opendate,price, episode)  ",
// 		"  VALUES(#{obj.title}, #{obj.keyword}, #{obj.pd}, #{obj.chkage}, #{obj.opendate}, #{obj.price}, #{obj.episode})  "
// 	})
// 	public int videolistInsert(@Param("obj") Videolist obj);
// 	//관리자용 영상 수정
// 	@Update({
// 		"  UPDATE videolist SET title=#{obj.title}, keyword= #{obj.keyword}, pd=#{obj.pd}, chkage=#{obj.chkage}, opendate=#{obj.opendate} ,price=#{obj.price}  ",
// 	    "  WHERE  title=#{nowtitle}  "
// 	})
// 	public int videolistUpdate(@Param("obj") Videolist obj, @Param("nowtitle") String nowtitle);
// 	//관리자용 영상 삭제
// 	@Delete({
// 		"  DELETE FROM videolist WHERE title=#{title}  "
// 	})
// 	public int videolistDelete(@Param("title") String title);
// 	//작품검색(제목,키워드,감독,배우)
// 	@Select({ "  SELECT *  FROM VIDEOLISTVIEW  WHERE EPISODE =1 AND ${comboboxvalue}  LIKE '%'||#{search}||'%'  " })
// 	public List<VideolistView> videolistSearch( @Param("comboboxvalue") String comboboxvalue,@Param("search") String search);
// 	//분류 검색(영화, 드라마, 애니)
// 	@Select({ "  SELECT v.* FROM VIDEOLISTVIEW v WHERE EPISODE =1 AND v.KEYWORD  LIKE '%'||#{category}||'%'  " })
// 	public List<VideolistView> videolistGroupSearch(@Param("category") String category);
// 	//분류하위 항목 장르검색
// 	@Select({ "  SELECT v.*  FROM VIDEOLISTVIEW v WHERE EPISODE =1 AND v.KEYWORD LIKE '%'||#{category}||'%' AND v.KEYWORD LIKE '%'||#{genre}||'%'  " })
// 	public List<VideolistView> videolistGroupKeywordButton(@Param("category") String category, @Param("genre") String genre);
// 	//연령제한
// 	@Select({ "  SELECTv.CHKAGE  FROM VIDEOLISTVIEW v WHERE EPISODE =1 AND videocode=#{videocode}  " })
// 	public Videolist videolistCHKage(@Param("videocode") Long videocode);
// 	//작품개봉날짜 순서
// 	@Select({ "  SELECT v.*  FROM VIDEOLISTVIEW v WHERE EPISODE =1 ORDER BY v.opendate  DESC  " })
// 	public List<VideolistView> videolistRecently();
	
// 	//배우목록 출력
// 	@Select({
// 		"  SELECT * FROM ACTORS  "
// 	})
// 	public List<Actors> selectActors();
// 	//현재 작품에 등록된 배우코드 출력
// 	@Select({
// 		"  SELECT actors_no FROM casts WHERE videocode=#{videocode.videocode}  "
// 	})
// 	public List<Long> selectActorsinvideo(@Param("videocode") Casts videocode);
// 	//배우코드로 배우 출력
// 	@Select({
// 		"  SELECT actors_no, actors_name FROM actors WHERE actors_no =#{code}"
// 	})
// 	public Actors selectnotoname(@Param("code") Long code);
// 	//제목으로 비디오코드 출력
// 	@Select({
// 		"  SELECT videocode FROM videolist WHERE title=#{title}  AND episode =1  "
// 	})
// 	public Long selectnofromtitle(@Param("title") String title);
// 	//배우목록에서 이름 검색
// 	@Select({
// 		"  SELECT * FROM ACTORS WHERE actors_name LIKE  '%'||#{search}||'%'  "
// 	})
// 	public List<Actors> selectActorsname(@Param("search") String search);
// 	//casts에 배우추가
// 	@Insert({
// 		"  INSERT INTO casts (videocode, actors_no)  ",
// 		"  VALUES(#{videocode}, #{actors_no})  "
// 	})
// 	public int castsInsertactor(@Param("videocode") Long videocode, @Param("actors_no") Long actors_no);
// 	//casts에 배우삭제
// 	@Delete({
// 		"  DELETE FROM casts WHERE videocode=#{videocode} AND actors_no=#{actors_no}  "
// 	})
// 	public int castsDeleteactor(@Param("videocode") Long videocode, @Param("actors_no") Long actors_no);
// 	//actors에 배우가 없으면 추가
// 	@Insert({
// 		"  INSERT INTO actors (actors_name)  ",
// 		"  VALUES(#{actors_name})  "
// 	})
// 	public int actorInsert(@Param("actors_name") String actors_name);
// 	//현재등록된 배우 중복확인
// 	@Select({
// 		"  SELECT COUNT(*) cnt FROM casts WHERE videocode=#{videocode} AND actors_no=#{actors_no} "
// 	})
// 	public int  castsInsertactorchk(@Param("actors_no") Long actors_no, @Param("videocode") Long videocode  );
	
// 	@Select({
// 		" SELECT v.* FROM videolist v WHERE ${column} LIKE '%' || #{text} || '%' ORDER BY videocode ASC "
// 	})
// 	public List<Videolist> selectVideoList(@Param("column") String column, 
// 			@Param("text")String text);
// }
}
