package com.project.controller.msh;

import java.math.BigInteger;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.dto.Board;
import com.project.mapper.QnaMapper;
import com.project.service.msh.QnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/qna")
public class QnAController {
    final String format = "QnA => {}";
    final QnaService qnaService;
    final QnaMapper qnaMapper;

    // 문의글 목록
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model) {
        List<com.project.entity.Board> list = qnaService.selectBoardList();
        model.addAttribute("list", list);
        return "/msh/selectlist";
    }

    // 문의글 등록GET
    @GetMapping(value = "/insert.do")
    public String insertGET() {
        try {
            return "/msh/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 문의글 등록POST
    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Board board) {
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            board.setPassword(bcpe.encode(board.getPassword()));
            int ret = qnaService.insertBoard(board);
            log.info(format, board.toString());
            log.info("ret = {}", ret);

            return "redirect:/qna/selectlist.do"; // return "/msh/selectlist"일때 list 못받아오고 url안바뀜
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 문의글 조회
    @GetMapping(value = "/selectone.do")
    public String selectoneGET(Model model, @RequestParam(name = "no") Long no,@RequestParam(name = "profileno") Long profileno) {
        
        Board board = qnaService.selectoneBoard();
        log.info("QnASelectone = {}", board.toString());
        model.addAttribute("board", board);
        return "/msh/selectone";
    }

    // 문의글 수정
    @GetMapping(value = "/update.do")
    public String updateGET(@RequestParam(name = "no", defaultValue = "0", required = false) Long no, Model model) {
        if (no == 0) { // 번호 없으면 다시 목록으로 돌아감
            return "redirect:selectlist.do";
        }
        Board board = qnaService.selectoneBoard(no); // no에 맞는 글 조회
        log.info("QnAUpdate of selectone = {}", board); //수정화면으로 넘어와서의 board값

        model.addAttribute("board", board); // "board"에 조회된 board를 담아서 update.html로 보냄
        return "/msh/update";
    }
    @PostMapping(value = "/update.do")
    public String updatePOST(@ModelAttribute Board board) {
        int ret = qnaService.updateBoard(board);
        log.info("QnAUpdate = {}", board);
        if (ret == 1) {
            return "redirect:selectone.do?no=" + board.getNo();
        }
        return "redirect:selectlist.do"; // 실패시 목록으로 감
    }

}
