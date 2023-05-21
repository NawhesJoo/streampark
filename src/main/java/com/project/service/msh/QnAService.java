package com.project.service.msh;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.entity.Board;

@Service
public interface QnAService {

    // 문의글 전체 목록
    public List<Board> selectBoardList();

}
