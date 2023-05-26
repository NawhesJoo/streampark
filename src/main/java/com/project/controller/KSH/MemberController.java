package com.project.controller.KSH;

import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Member;
import com.project.repository.KSH.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    final HttpSession httpSession;
    final MemberRepository mRepository;
    final String format = "membercontroller => {}";
    BigInteger token = new BigInteger("0");
    Date date = new Date();

    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 메인 페이지
    @GetMapping(value = "/main.do")
    public String mainGET() {
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
            obj.setPw(bcpe.encode(obj.getPw()));
            obj.setRole("C");
            obj.setToken(token);
            obj.setRegdate(date);
            log.info(format, obj.toString());

            mRepository.save(obj);
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
    public String logoutGET(){
        httpSession.invalidate();
        return "redirect:/member/main.do";
    }
    // 로그인 페이지
    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "KSH/login";
    }

    @PostMapping(value = "/login.do")
    public String loginPOST(@ModelAttribute Member obj, Model model) {
        try {
            Member obj1 = mRepository.findById(obj.getId()).orElse(null);
            if (bcpe.matches(obj.getPw(), obj1.getPw())) {
                httpSession.setAttribute("id", obj.getId());
                httpSession.setAttribute("role", obj.getRole());
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
    public String findPwGET(Model model) {
        model.addAttribute("pwChangeOk", 0);
        return "KSH/findPw";
    }

    @PostMapping(value = "/findpw.do")
    public String findPwPOST(Model model,
            @RequestParam(name = "id1") String id,
            @RequestParam(name = "changePw") String changePw) {
        try {
            log.info("{},{}", id, changePw);
            Member obj1 = mRepository.findById(id).get();
            obj1.setPw(bcpe.encode(changePw));
            mRepository.save(obj1);

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
        @RequestParam(name = "menu", defaultValue = "0", required = false)String menu){
        String id = "1";
            log.info("{}",menu);
        try {
            if(menu.equals("0")){
                menu = "1";
                return "redirect:/member/info.do?menu=" + menu; 
            }
            if(menu.equals("1")){
                Member member = mRepository.findById(id).get();
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
        @RequestParam(name = "pw", required = false)String pw,
        @RequestParam(name = "newpw",required = false)String newpw,
        @RequestParam(name = "menu",required = false)String menu){
            String id = "1";
            String myInfoChanged = "false";
            try {
                // 정보수정
                if(menu.equals("1")){
                    Member obj1 = mRepository.findById(id).get();
                    obj1.setName(obj.getName());
                    obj1.setEmail(obj.getEmail());
                    obj1.setBirth(obj.getBirth());
                    obj1.setPhone(obj.getPhone());
                    obj1.setGender(obj.getGender());
                    Member ret = mRepository.save(obj1);

                    if(ret != null){
                        model.addAttribute("myInfoChanged", true); //  변경 성공 여부를 모델에 추가
                        myInfoChanged = "true";
                    }
                    else{
                        model.addAttribute("myInfoChanged", false); //  변경 성공 여부를 모델에 추가
                        myInfoChanged = "false";
                    }
                }
                log.info("aaaaaaaaaaaaaa =>{}, {}, {}",id, pw, newpw);
                // 비밀번호 수정
                if(menu.equals("2")){
                    Member obj1 = mRepository.findById(id).get();
                    log.info("aaaaaaaaaaaaaa =>  {}", obj1.toString());
                    if(bcpe.matches(pw,obj1.getPw())){
                        obj1.setPw(bcpe.encode(newpw));
                        mRepository.save(obj1);
                        model.addAttribute("myInfoChanged", true); //  변경 성공 여부를 모델에 추가
                        myInfoChanged = "true";
                    }
                    else if(!bcpe.matches(pw,obj1.getPw())){
                        model.addAttribute("myInfoChanged", false); //  변경 성공 여부를 모델에 추가
                        myInfoChanged = "false";
                    }
                }
                if(menu.equals("3")){
                    try {
                        mRepository.deleteById(id);
                       return "redirect:/member/main.do";
                    } catch (Exception e) {
                        e.printStackTrace();
                        model.addAttribute("myInfoChanged", false); //  변경 성공 여부를 모델에 추가
                        myInfoChanged = "false";
                    }
                }
                return "redirect:/member/info.do?menu=" + menu + "&myInfoChanged=" + myInfoChanged;
            } 
            catch (Exception e) {
                e.printStackTrace();
                return "redirect:/member/info.do?menu=" + menu; 
            }
    }
}
