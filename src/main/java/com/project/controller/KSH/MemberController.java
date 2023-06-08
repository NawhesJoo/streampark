package com.project.controller.KSH;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.project.entity.Member;
import com.project.repository.MemberRepository;
import com.project.service.KSH.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    final HttpSession httpSession;
    final MemberRepository mRepository;
    final MemberService mService;
    final String format = "membercontroller => {}";
    BigInteger token = new BigInteger("0");
    Date date = new Date();

    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 메인 페이지
    @GetMapping(value = "/main.do")
    public String mainGET(Model model) {
        List<String> imageUrls = Arrays.asList(
        "/streampark/images/KSH/backgroundImg1.jpg",
        "/streampark/images/KSH/backgroundImg2.jpg",
        "/streampark/images/KSH/backgroundImg3.jpg",
        "/streampark/images/KSH/backgroundImg4.jpg",
        "/streampark/images/KSH/backgroundImg5.jpg"
        );
        model.addAttribute("imageUrls", imageUrls);
        return "/KSH/main";
    }

    // 가입페이지
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/KSH/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member obj) {
        try {
            mService.insertMember(obj);
            return "redirect:/member/wellcome.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/join.do";
        }
    }

    // 웰컴 페이지
    @GetMapping(value = "/wellcome.do")
    public String wellcomeGET() {
        return "KSH/wellcome";
    }

    // 로그아웃
    @GetMapping(value = "logout.do")
    public String logoutGET() {
        httpSession.invalidate();
        return "redirect:/member/main.do";
    }

    // 로그인 페이지
    @GetMapping(value = "/login.do")
    public String loginGET(HttpServletRequest request, HttpServletResponse response) {
        return "KSH/login";
    }

    @PostMapping(value = "/login.do")
    public String loginPOST(@ModelAttribute Member obj) {
        try {
            int i = mService.login(obj);
            if (i == 1) {
                return "redirect:/Profile/create.do";
            } else {
                return "redirect:/member/login.do";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/login.do";
        }
    }

    // 아이디 찾기
    @GetMapping(value = "/findid.do")
    public String findIdGET() {
        return "KSH/findid";
    }

    @PostMapping(value = "/findid.do")
    public String findIdPOST() {
        return "KSH/findid";
    }

    // 비밀번호 찾기
    @GetMapping(value = "/findpw.do")
    public String findPwGET() {
        return "KSH/findPw";
    }

    @PostMapping(value = "/findpw.do")
    public String findPwPOST(Model model,
            @RequestParam(name = "id1") String id,
            @RequestParam(name = "changePw") String changePw) {
        try {
            mService.updatePw(id, changePw);
            model.addAttribute("passwordChanged", true); // 비밀번호 변경 성공 여부를 모델에 추가
            return "redirect:/member/findpw.do?passwordChanged=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/findpw.do";
        }
    }

    // 정보 페이지
    @GetMapping(value = "/info.do")
    public String infoGET(Model model,
            @RequestParam(name = "menu", defaultValue = "0", required = false) String menu,  @AuthenticationPrincipal User user) {
        String id = user.getUsername();
        log.info("{}", menu);
        try {
            if (menu.equals("0")) {
                menu = "1";
                return "redirect:/member/info.do?menu=" + menu;
            }
            if (menu.equals("1")) {
                Member member = mService.findById(id);
                model.addAttribute("member", member);
            }
            model.addAttribute("menu", menu);
            return "/KSH/infomenus/info";
        } catch (Exception e) {
            e.printStackTrace();
            return "/KSH/infomenus/info";
        }
    }

    @PostMapping(value = "/info.do")
    public String infoPOST(@ModelAttribute Member obj, Model model,
            @RequestParam(name = "pw", required = false) String pw,
            @RequestParam(name = "newpw", required = false) String newpw,
            @RequestParam(name = "menu", required = false) String menu,
            @AuthenticationPrincipal User user) {
        String id = user.getUsername();
        String myInfoChanged = "false";
        try {
            // 정보수정
            if (menu.equals("1")) {
                Member ret = mService.updateMemberInfo(id, obj);
                if (ret != null) {
                    model.addAttribute("myInfoChanged", true); // 변경 성공 여부를 모델에 추가
                    myInfoChanged = "true";
                } else {
                    model.addAttribute("myInfoChanged", false); // 변경 성공 여부를 모델에 추가
                    myInfoChanged = "false";
                }
            }
            log.info("aaaaaaaaaaaaaa =>{}, {}, {}", id, pw, newpw);
            // 비밀번호 수정
            if (menu.equals("2")) {
                int ret = mService.updateMemberInfoPw(id, pw, newpw);
                if (ret == 1) {
                    model.addAttribute("myInfoChanged", true); // 변경 성공 여부를 모델에 추가
                    myInfoChanged = "true";
                } else if (ret == 0) {
                    model.addAttribute("myInfoChanged", false); // 변경 성공 여부를 모델에 추가
                    myInfoChanged = "false";
                } else {
                    model.addAttribute("myInfoChanged", false); // 변경 성공 여부를 모델에 추가
                    myInfoChanged = "false";
                }
            }
            if (menu.equals("3")) {
                try {
                    mService.deleteById(id);
                    return "redirect:/member/main.do";
                } catch (Exception e) {
                    e.printStackTrace();
                    model.addAttribute("myInfoChanged", false); // 변경 성공 여부를 모델에 추가
                    myInfoChanged = "false";
                }
            }
            return "redirect:/member/info.do?menu=" + menu + "&myInfoChanged=" + myInfoChanged;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/info.do?menu=" + menu;
        }
    }
}
