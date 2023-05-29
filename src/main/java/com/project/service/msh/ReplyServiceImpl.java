package com.project.service.msh;

import org.springframework.stereotype.Service;

import com.project.dto.QnaReply;
import com.project.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    final ReplyMapper rMapper;

    // 답변등록
    @Override
    public int insertReply(QnaReply qnaReply) {
        try {
            return rMapper.insertReply(qnaReply);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 답변조회
    @Override
    public QnaReply selectoneReply(Long no) {
        try {
            return rMapper.selectoneReply(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 답변 수정
    @Override
    public int updateReply(QnaReply qnaReply) {
        try {
            return rMapper.updateReply(qnaReply);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 답변 삭제
    @Override
    public int deleteReply(QnaReply qnaReply) {
        try {
            return rMapper.deleteReply(qnaReply);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
