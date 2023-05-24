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
    public String insertPOST(@ModelAttribute com.project.dto.Board board) {
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
    public String selectoneGET(Model model, @RequestParam(name = "no") Long no) {
        // log.info("no = {}",no);
        com.project.dto.Board board = qnaService.selectoneBoard(no);
        // log.info(format, board.toString());
        model.addAttribute("board", board);
        return "/msh/selectone";
    }

    // 문의글 수정
    @GetMapping(value = "/update.do")
    public String updateGET(@RequestParam(name = "no") long no, Model model) {
        if (no == 0) {
            return "redirect:selectlist.do";
        }
        Board obj = qnaService.selectoneBoard(no);
        model.addAttribute("obj", obj);
        return "/board/update";
    }

    @PostMapping(value = "/update.do")
    public String updatePOST(@ModelAttribute Board board) {
        int ret = qnaService.updateBoard(board);

        if (ret == 1) {
            return "redirect:selectone.do?no=" + board.getNo();
        }
        return "redirect:update.do?no=" + board.getNo(); // 실패시 수정화면
    }

}
