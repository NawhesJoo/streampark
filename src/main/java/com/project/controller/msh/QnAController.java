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

import com.project.entity.Board;
import com.project.entity.Profile;
import com.project.mapper.QnaMapper;
import com.project.repository.msh.QnARepository;
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
    final QnaMapper qnAMapper;

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
    public String insertPOST(@ModelAttribute com.project.dto.Board board) {
        try { 
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            board.setPassword(bcpe.encode(board.getPassword()));
            int ret = qnaService.insertBoard(board);
            log.info(format, board.toString());
            log.info(format, ret);

            return "redirect:/qna/selectlist.do"; // return "/msh/selectlist"일때 list 못받아오고 url안바뀜
            // return "redirect:/qna/selectone.do"; 나중에 바로 조회화면으로 할거임
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 문의글 조회
    @GetMapping(value="/selectone.do")
    public String selectoneGET(Model model,@RequestParam(name = "no") BigInteger no){
        com.project.dto.Board board = qnaService.selectoneBoard(no);
        log.info(format, board.toString());
        model.addAttribute("board", board);
        return "/msh/selectone";
    }
    
    // 글삭제
    @GetMapping(value = "/delete.do")
    public String deldeteGET(@RequestParam(name = "no") Long no
                            ,@RequestParam(name = "password") String password) {
        try {
            com.project.dto.Board board = new com.project.dto.Board();
	        board.setNo(no);
            int ret = qnaService.deleteBoard(board);
            return "/msh/delete";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

}
