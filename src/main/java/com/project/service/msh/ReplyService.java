package com.project.service.msh;

import org.springframework.stereotype.Service;

import com.project.dto.QnaReply;

@Service
public interface ReplyService {

    // 답변 조회
    public QnaReply selectoneReply(Long no);

    // 답변 작성
    public int insertReply(QnaReply qnaReply);

    // 답변 수정
    public int updateReply(QnaReply qnaReply);

    // 답변 삭제
    public int deleteReply(QnaReply qnaReply);

}
