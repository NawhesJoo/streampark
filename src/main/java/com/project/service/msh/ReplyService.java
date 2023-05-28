package com.project.service.msh;

import org.springframework.stereotype.Service;

import com.project.dto.QnaReply;

@Service
public interface ReplyService {

    // 문의글 조회
    public QnaReply selectoneReply(Long no);

    // 문의글 작성
    public int insertReply(QnaReply qnaReply);

    // // 게시글수정
    // public int updateReply(QnaReply qnaReply);

    // // 게시글삭제
    // public int deleteReply(QnaReply qnaReply);

}
