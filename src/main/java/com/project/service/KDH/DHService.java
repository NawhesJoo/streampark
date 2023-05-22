package com.project.service.KDH;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dto.Actorsdto;
import com.project.dto.Castsdto;
import com.project.dto.VideolistView;
import com.project.dto.Videolistdto;
import com.project.entity.Member;

@Service
public interface DHService {

    //관리자용 영상 추가(회원의 권한 정보와, 비디오의 정보)
    public void videolistInsert(Member admin, Videolistdto obj);
	//작품이름으로 작품 코드 조회(영상을 조회하기위한 작품이름으로 작품코드 조회)
	public BigInteger selectnofromtitle(String title);
	//영상코드로 영상조회(조회된 작품코드로 작품을 조회한다.)
	public VideolistView selectvideoOne(BigInteger videocode );
	//관리자용 영상 수정(조회된 작품의 정보를 수정)
	public void  videolistUpdate(Member admin, Videolistdto obj, String nowtitle);
	//관리자용 영상 삭제(회원의 권한 정보와, 제목을 받아서 삭제)O
	public void  videolistDelete(Member admin, String title);

	//관리자용 작품에 배우 등록(작품이름으로 작품코드리스트를 받아서  작품리스트에 배우등록)
	public int addactorinvideo(Videolistdto videocode, Actorsdto no );
	//관리자용 배우 삭제(작품이름으로 작품코드리스트받아서 작품리스트에서 배우 삭제)
	public int removeactorinvideo(Videolistdto videocode, Castsdto no );
	//관리자용 전체배우 조회(전체 배우리스트 조회)
	public List<Actorsdto> selectactors();
	//관리자용 배우 목록 추가(전체 배우리스트에 없는 배우등록)
	public int addactorlist(String name);
	//관리자용 현재 작품에 등록된 배우코드 조회
	public List<Long> selectactorsinvideo(BigInteger videocode);
	//배우코드로 배우이름 조회
	public Actorsdto selectnotoname(Long no);
	//현재등록된 배우 중복확인
	public int  castsInsertactorchk(Long actors_no, Long videocode);


	//전체 작품목록 조회
	public List<VideolistView> selectvideolist();
	// // public List<Videolist> selectVideoList(@Param("column") String column, 
	// // 		@Param("text")String text);
	
	// //고객용 영상 검색
	// public List<VideolistView>  videolistSearch(String comboboxvalue, String  search);
	// //고객용 영상 분류검색
	// public List<VideolistView>  videolistGroupSearch(String category);
	// //고객용 영상 분류하위장르검색
	// public List<VideolistView>  videolistGroupKeywordButton(String category, String genre);
	// //고객용 영상 연령제한
	// public Videolist  videolistCHKage(Long videocode);
	// //고객용 영상 작품 개봉날짜 순서로 정렬
	// public List<VideolistView>  videolistRecently();


	// //작품 사진 등록
	// public int insertvideoimg(Videoimg videoimg);
	// //작품사진 수정
	// public int updatevideoimg(Videoimg videoimg);
	// //작품 사진 삭제(작품제목으로 코드 받아서  코드로 사진 번호 조회 후 번호로 삭제)
	// public int deletevideoimg(Videoimg vimgno);
	// //작품 사진 하나조회
	// public Videoimg selectvideoimgOne(Videoimg vimgno);

	// //제일많이 본영상 조회
	// public List<VideolistView> countplayingvideolist();

	// //리뷰 많은 수 조회
	// public List<VideolistView> countreviewvideolist();


}
