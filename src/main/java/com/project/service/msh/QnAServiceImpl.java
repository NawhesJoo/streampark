package com.project.service.msh;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dto.Board;
import com.project.mapper.QnaMapper;
import com.project.repository.msh.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {
    final QnaRepository qnaRepository;
    final QnaMapper qnaMapper;

    // 문의글 전체 목록
    @Override
    public List<com.project.entity.Board> selectBoardList() {
        return qnaRepository.findAllByOrderByNoDesc();
    }

    // 문의글 작성
    @Override
    public int insertBoard(Board board) {
        try {
            return qnaMapper.insertBoard(board);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 문의글 조회
    @Override
    public Board selectoneBoard(BigInteger no) {
        try {
            return qnaMapper.selectoneBoard(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 문의글 수정
    @Override
    public int updateBoard(Board board) {
        try {
            return qnaMapper.updateBoard(board);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 문의글 삭제
    @Override
    public int deleteBoard(Long no) {
        try {
            return qnaMapper.deleteBoard(no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
