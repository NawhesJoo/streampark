package com.project.controller.msh;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.dto.Board;
import com.project.dto.QnaReply;
import com.project.mapper.QnaMapper;
import com.project.repository.MemberRepository;
import com.project.repository.QnaRepository;
import com.project.service.msh.QnaService;
import com.project.service.msh.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/qna")
public class QnAController {
    final QnaService qnaService;
    final ReplyService replyService;
    final QnaMapper qnaMapper;
    final HttpSession httpSession;
    final QnaRepository qRepository;
    final MemberRepository memberRepository;

    // 문의글 목록
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        if (page == 0) {
            return "redirect:selectlist.do?page=1";
        }
        List<com.project.entity.Board> list = qnaService.selectBoardList();
        int pageSize = 10; // 한페이지에 나오는 아이템갯수
        int ret = list.size();
        int totalPages = (int) Math.ceil((double) ret / pageSize);
        int maxVisiblePages = 5; // 한 번에 보이는 최대 페이지 수

        // 현재 페이지를 기준으로 시작 페이지와 끝 페이지 계산
        int startPage = Math.max(1, page - maxVisiblePages / 2);
        int endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

        PageRequest pageRequest = PageRequest.of((page - 1), pageSize);

        String formattedRet;
        if (ret < 10) {
            formattedRet = "0" + ret;
        } else {
            formattedRet = String.valueOf(ret);
        }

        model.addAttribute("list", list);
        model.addAttribute("ret", formattedRet);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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
    public String insertPOST(@ModelAttribute Board board, HttpServletRequest request) {
        try {
            BigInteger profileno1 = (BigInteger) httpSession.getAttribute("profileno");
            Long profileno = profileno1.longValue();
            log.info("profileno = {}", profileno);
            if (profileno == null) {
                request.setAttribute("msg", "로그인이 필요합니다.");
                request.setAttribute("url", "/member/login");
                return "alert";
            }
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            board.setPassword(bcpe.encode(board.getPassword())); // 비밀번호를 해싱해서 저장
            board.setProfileno(profileno); // 세션에서 프로필번호를 Long으로 변환해서 저장
            int ret = qnaService.insertBoard(board); // mapper구현
            log.info("insertBoard = {}", board.toString());
            log.info("ret = {}", ret);

            return "redirect:/qna/selectlist.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 내가 쓴 문의글 조회
    @GetMapping(value = "/selectone.do")
    public String selectoneGET(Model model, @RequestParam(name = "no") Long no, @AuthenticationPrincipal User user) {
        try {
            BigInteger profilenoSession = (BigInteger) httpSession.getAttribute("profileno");
            Long profileno = profilenoSession.longValue();
            String id = user.getUsername();
            String role = memberRepository.findById(id).get().getRole(); // 계정의 id를 조회해서 role가져옴
            model.addAttribute("role", role); // role=C이면 답변부분 버튼 안나오게 함
            // log.info("role = {}", role);
            log.info("profileno = {}", profilenoSession);
            // log.info("role = {}", role);

            // 답변부분
            QnaReply reply = replyService.selectoneReply(no); // no에 해당하는 답변 조회
            log.info("reply = {}", reply); // reply 넘겨받음

            // 문의부분
            Board obj = new Board();
            obj.setNo(no);
            log.info("obj = {}", obj.toString()); // no 확인용
            Board board = qnaService.selectoneBoard(no);
            log.info("QnASelectone = {}", board.toString());

            if (role.equals("C") && board.getProfileno().equals(profileno) || role.equals("A")) {
                model.addAttribute("board", board);
                model.addAttribute("reply", reply);
                return "/msh/selectone";
            } else { // 본인게시글이 아닌경우 에러메시지
                model.addAttribute("errorMessage");
                return "/msh/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/qna/selectlist.do";
        }
    }

    // 문의글 수정 GET
    @GetMapping(value = "/update.do")
    public String updateGET(@RequestParam(name = "no") Long no, Model model) {
        if (no == 0) { // 번호 없으면 다시 목록으로 돌아감
            return "redirect:selectlist.do";
        }
        BigInteger profilenoSession = (BigInteger) httpSession.getAttribute("profileno");
        Long profileno = profilenoSession.longValue();
        // log.info("profileno = {}", profileno);

        // Board obj = new Board();
        // obj.setNo(no);
        // obj.setProfileno(profileno);
        // log.info("QnASelectone = {}", obj.toString());

        Board board = qnaService.selectoneBoard(no); // no, profileno에 맞는 글 조회
        // log.info("Before Update = {}", board); // 비밀번호 입력 후 수정화면에서의 기존의 board값
        model.addAttribute("board", board); // "board"에 조회된 board를 담아서 update.html로 보냄
        return "/msh/update";
    }

    // 문의글 수정 POST
    @PostMapping(value = "/update.do")
    public String updatePOST(@ModelAttribute Board board) {
        int ret = qnaService.updateBoard(board);
        log.info("After Update = {}", board);
        if (ret == 1) {
            return "redirect:selectone.do?no=" + board.getNo();
        }
        return "redirect:selectlist.do"; // 실패시 목록으로 감
    }

    // 답변 등록GET
    @GetMapping(value = "/reply/insert")
    public String insertReplyGET(Model model, @RequestParam(name = "no") Long no, @AuthenticationPrincipal User user) {
        try {
            String id = user.getUsername();
            String role = memberRepository.findById(id).get().getRole(); // 계정의 id를 조회해서 role가져옴
            model.addAttribute("boardNo", no);
            model.addAttribute("role", role);
            return "/msh/insertReply";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 답변 등록POST
    @PostMapping(value = "/reply/insert")
    public String insertReplyPOST(@ModelAttribute QnaReply qnaReply, HttpServletRequest request) {
        try {

            int ret = replyService.insertReply(qnaReply);
            log.info("insertReply = {}", qnaReply.toString());
            log.info("ret = {}", ret);
            return "redirect:/qna/selectlist.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 답변 수정 GET
    @GetMapping(value = "/reply/update")
    public String updateReplyGET(@RequestParam(name = "no") Long no, Model model) {
        if (no == 0) { // 번호 없으면 다시 목록으로 돌아감
            return "redirect:selectlist.do";
        }
        BigInteger profilenoSession = (BigInteger) httpSession.getAttribute("profileno");
        Long profileno = profilenoSession.longValue();
        // log.info("profileno = {}", profileno);

        QnaReply obj = new QnaReply();
        obj.setNo(no);
        obj.setProfileno(profileno);
        // log.info("QnASelectone = {}", obj.toString());

        QnaReply reply = replyService.selectoneReply(no); // no에 맞는 답변 조회
        log.info("Before Update Reply = {}", reply); // 수정화면으로 넘어와서의 reply값

        model.addAttribute("reply", reply); // "reply"에 조회된 reply 담아서 updateReply.html로 보냄
        return "/msh/updateReply";
    }

    // 답변 수정 POST
    @PostMapping(value = "/reply/update")
    public String updateReplyPOST(@ModelAttribute QnaReply qnaReply) {
        int ret = replyService.updateReply(qnaReply);
        log.info("ret = {}", ret);
        log.info("After Update Reply = {}", qnaReply);
        if (ret == 1) {
            return "redirect:/qna/selectone.do?no=" + qnaReply.getNo();
        }
        return "redirect:/qna/selectlist.do"; // 실패시 목록으로 감
    }

}
