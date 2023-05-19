package com.project.service.msh;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.entity.Board;
import com.project.repository.msh.QnARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    final QnARepository qnaRepository;

    @Override
    public List<Board> selectBoardList() {
            return qnaRepository.findAllByOrderByNoDesc();
    }
}
