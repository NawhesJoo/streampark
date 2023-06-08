package com.project.service.msh;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dto.Board;

@Service
public interface QnaService {

    // 문의글 전체 목록
    public Page<com.project.entity.Board> pageList(Pageable pageable);

    // 문의글 작성
    public int insertBoard(Board board);

    // 문의글 조회
    public Board selectoneBoard(Long no);

    // 게시글수정
    public int updateBoard(Board board);

    // 게시글삭제
    public int deleteBoard(Board board);

}
