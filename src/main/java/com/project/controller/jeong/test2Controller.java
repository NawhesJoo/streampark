package com.project.controller.jeong;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Board;
import com.project.entity.Qnareply;
import com.project.repository.JeongRepositories.BoardRepository;
import com.project.repository.JeongRepositories.MemberRepository;
import com.project.repository.JeongRepositories.ProfileRepository;
import com.project.repository.JeongRepositories.QnareplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/test2")
public class test2Controller {

    final MemberRepository mRepository;
    final BoardRepository bRepository;
    final ProfileRepository pRepository;
    final QnareplyRepository qRepository;

    @GetMapping(value = "test1.do")
    public String test1GET(Model model) {        
        log.info("{}", "테스트 접속");
        log.info("{}",mRepository.findById("id").orElse(null));

        return "/jeong/test/test1";        
    }

    @GetMapping(value = "test2.do")
    public String test2GET() {
        try {
            // Member test = mRepository.findById("id1").orElse(null);
            // Profile test = pRepository.findById(BigInteger.valueOf(31)).orElse(null);
            // log.info("test1 -> {}", test);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "jeong/test/index.html";
    }
}
