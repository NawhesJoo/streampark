package com.project.service.msh;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dto.Board;


@Service
public interface QnaService {

    // 문의글 전체 목록
    public List<com.project.entity.Board> selectBoardList(); 

    // 문의글 작성
    public int insertBoard(Board board);

    // 문의글 조회
    public Board selectoneBoard(BigInteger no);

    //게시글수정
	public int updateBoard(Board board);
	
	//게시글삭제
	public int deleteBoard(Long no);

}
