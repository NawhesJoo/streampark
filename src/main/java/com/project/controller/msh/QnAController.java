package com.project.controller.msh;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    final QnAService qnaService;
    final QnARepository qnaRepository;

    @GetMapping(value="/selectlist.do")
    public String selectlistGET(Model model) {
        List<Board> list = qnaRepository.findAllByOrderByNoDesc();
        model.addAttribute("list", list);
        return "/msh/selectlist";
    }
    
}
