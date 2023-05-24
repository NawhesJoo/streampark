package com.project.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.Board;
import com.project.service.msh.QnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RestController
@RequestMapping(value = "/api/qna")
@RequiredArgsConstructor
public class RestQnAController {
    final QnaService qnaService;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    // final String format = "password => {}";

    // 문의글 수정
    @PutMapping(value = "/update.do")
    public Map<String, Object> updateQnA(@RequestBody Board board) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            log.info("Board = {}", board.toString());
            Board ret = qnaService.selectoneBoard(board.getNo());
            log.info("{}", ret);

            // 실패시 전송할 데이터, 밑으로 가면 200에서 다시 0으로 바뀌니까 위에 있음.
            retMap.put("status", 0);

            log.info("password = {}", bcpe.encode(board.getPassword()));
            if (bcpe.matches(board.getPassword(), ret.getPassword())) {
                // 수정 작업 수행
                qnaService.updateBoard(board);
                retMap.put("status", 200);
                retMap.put("ret", 1);
            }
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
            log.info("Board = {}", board.toString());
            Board ret = qnaService.selectoneBoard(board.getNo());
            log.info("{}", ret);

            // 실패시 전송할 데이터, 밑으로 가면 200에서 다시 0으로 바뀌니까 위에 있음.
            retMap.put("status", 0);

            log.info("password = {}", bcpe.encode(board.getPassword()));
            if (bcpe.matches(board.getPassword(), ret.getPassword())) {
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

}
