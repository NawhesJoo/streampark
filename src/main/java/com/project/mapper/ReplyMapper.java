package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.QnaReply;

@Mapper
public interface ReplyMapper {

    // 문의글 조회
    public QnaReply selectoneReply(Long no);

    // 답변 작성
    public int insertReply(QnaReply qnaReply);

    // 답변 수정
    public int updateReply(QnaReply qnaReply);

    // 답변 삭제
    public int deleteReply(QnaReply qnaReply);

}
