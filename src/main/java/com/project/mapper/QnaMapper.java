package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.Board;
import com.project.dto.msh.SearchDto;

@Mapper
public interface QnaMapper {

    // 문의글 작성
    public int insertBoard(Board board);

    // 문의글 조회
    public Board selectoneBoard(Long no);

    // 게시글수정
    public int updateBoard(Board board);

    // 게시글삭제
    public int deleteBoard(Board board);

    //페이지네이션
    List<Board> findAll(SearchDto params);
}