package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.Board;

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

}
