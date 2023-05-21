package com.project.controller.msh;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Board;
import com.project.repository.msh.QnARepository;
import com.project.service.msh.QnAService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/qna")
public class QnAController {
    final String format = "QnA => {}";
    final QnAService qnaService;
    final QnARepository qnaRepository;

    // 문의글 목록
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model) {
        List<Board> list = qnaService.selectBoardList();
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
            log.info(format, board.toString());

            qnaRepository.save(board); // repository에 저장 => insert
            return "redirect:/qna/selectlist.do"; // return "/msh/selectlist"일때 selectlist데이터 못받아오고 url안바뀜
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

}
