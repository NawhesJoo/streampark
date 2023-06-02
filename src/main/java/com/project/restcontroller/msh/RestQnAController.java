package com.project.restcontroller.msh;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.Board;
import com.project.service.msh.QnaService;
import com.project.service.msh.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RestController
@RequestMapping(value = "/api/qna")
@RequiredArgsConstructor
public class RestQnAController {
    final QnaService qnaService;
    final ReplyService replyService;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 문의글 수정
    @PutMapping(value = "/update.do")
    public Map<String, Object> updateQnA(@RequestBody Board board) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            BigInteger profilenoSession = (BigInteger) httpSession.getAttribute("profileno");
            Long profileno = profilenoSession.longValue();
            board.setProfileno(profileno);
            // log.info("AfterInputPW = {}", board.toString()); //no,입력한 password,profileno
            // 3개만 나옴
            Board retBoard = qnaService.selectoneBoard(board.getNo());
            // log.info("AfterInputPWSelect = {}", retBoard); // no,profileno에 맞는 retBoard가
            // 나옴

            // 실패시 전송할 데이터, 밑으로 가면 200에서 다시 0으로 바뀌니까 위에 있음.
            retMap.put("status", 0);

            // log.info("password = {}", bcpe.encode(board.getPassword())); //해시된 비번나옴
            if (bcpe.matches(board.getPassword(), retBoard.getPassword())) {
                // 수정 작업 수행
                retMap.put("status", 200);
                retMap.put("ret", 1);
            }
            // log.info("LastStep = {}", retBoard); //여기까지는 제목,내용 나옴 근데 DB에는 없음
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    // 문의글 삭제
    @DeleteMapping(value = "/delete.do")
    public Map<String, Object> deleteQnA(@RequestBody Board board) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            BigInteger profilenoSession = (BigInteger) httpSession.getAttribute("profileno");
            Long profileno = profilenoSession.longValue();
            board.setProfileno(profileno);
            log.info("Board = {}", board.toString()); // no, password, profileno나옴
            Board retBoard = qnaService.selectoneBoard(board.getNo());
            log.info("retBoard = {}", retBoard); // no,profileno에 맞는 retBoard가 나옴

            // 실패시 전송할 데이터, 밑으로 가면 200에서 다시 0으로 바뀌니까 위에 있음.
            retMap.put("status", 0);

            // log.info("password = {}", bcpe.encode(board.getPassword())); //해시된 비번나옴
            if (bcpe.matches(board.getPassword(), retBoard.getPassword())) {
                // 삭제 작업 수행
                qnaService.deleteBoard(board);
                retMap.put("status", 200);
                retMap.put("ret", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    // // 답변 작성
    // @PostMapping("/reply.do")
    // public String replyInsert(@RequestBody QnaReply qnaReply) {
    // try {
    // log.info("Body = {}", qnaReply);
    // int ret = replyService.insertReply(qnaReply);
    // log.info("ret = {}", ret);
    // return null;
    // } catch (Exception e) {
    // e.printStackTrace();
    // return "redirect:selectlist.do";
    // }
    // }

} // RestQnAController닫음
