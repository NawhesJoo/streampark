package com.project.controller.JSH;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.entity.Profileimg;
import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.MemberRepository;
import com.project.repository.ProfileRepository;
import com.project.repository.ProfileimgRepository;
import com.project.service.JSH.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {

    final String format = "ProfileContrller => {}";
    final ProfileService pService;
    final ProfileMapper pMapper;
    final MemberRepository mRepository;
    final ProfileRepository pRepository;
    final ProfileimgRepository piRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    @Value("${default.image}")
    private String DEFAULTIMAGE;
    final ResourceLoader resourceLoader;


    @GetMapping(value = "/profileimage")
    public ResponseEntity<byte[]> image(@RequestParam(name = "profileno", defaultValue = "0") BigInteger profileno) throws IOException{
        Profileimg obj = piRepository.findByProfile_Profileno(profileno);
        HttpHeaders headers = new HttpHeaders(); // import org.springframework.http.HttpHeaders;

        if( obj != null ){ // 이미지가 존재할 경우
                headers.setContentType( MediaType.parseMediaType( obj.getFiletype() ) );
                return new ResponseEntity<>( obj.getFiledata() , headers, HttpStatus.OK);
        }
        
        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream(); // exception 발생됨.
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>( is.readAllBytes(), headers, HttpStatus.OK);
    }

    // 프로필 선택창
    // paychk에서 가장 최신의 M(멤버쉽)하나를 가져와서 regdate 만료 확인(+ 30일)
    // 현재 멤버쉽은 GRADE 값
    @GetMapping(value = "/profilelist.do")
    public String profilelistGET(Model model, HttpSession session) {
        try {
            // String id = (String) session.getAttribute("id");
            String id = "a1";
            log.info("list id => {}", id);

            ArrayList<Profile> list = pService.selectprofile(id);
            model.addAttribute("list", list);
            for (Profile profile : list) {
                BigInteger profileno = profile.getProfileno();
                Profileimg profileimg = piRepository.findByProfile_Profileno(profileno);
    
                if (profileimg != null) {
                    model.addAttribute("profileno", profileno);
                    break;
                }
            }
            // log.info("list => {}", list.toString());

            // 만료 날짜
            List<Paychk> list1 = pMapper.selectPaychk(id);
            Paychk latestPaychk = list1.get(0);

            log.info("paychk => {}", latestPaychk.toString());

            // 만료 날짜와 현재 시간 비교
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(latestPaychk.getRegdate());
            calendar.add(Calendar.MONTH, 1);
            Date oneMonthAfter = calendar.getTime();

            // 현재 날짜 가져오기
            Date currentDate = new Date();

            // 날짜 비교
            if (currentDate.after(oneMonthAfter)) { // 만료되었다면 
                model.addAttribute("chk", "0");
            } else { // 아직 남아있을 때
                model.addAttribute("chk", "1");
            }


            session.removeAttribute("nickname");
            session.removeAttribute("profileno");
            return "/JSH/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "/JSH/list";
        }
    }
    


    // 프로필 생성
    @GetMapping(value = "/create.do")
        public String createGET(Model model){
            model.addAttribute("profile", new Profile());
        return "/JSH/create";
    }

    @PostMapping(value = "/create.do")
        public String createPOST(@ModelAttribute("profile") Profile profile,
            @RequestParam("nickname") String nickname,
            HttpSession session) {
            // 세션에서 멤버 ID 가져오기
            // String memberId = (String) session.getAttribute("id");
            String memberId = "a1";

            // 프로필 정보 설정
            Member member = new Member();
            member.setId(memberId);
            profile.setMember(member);
            profile.setNickname(nickname);
            //log.info("profile =>", profile);
            // 프로필 저장
            pRepository.save(profile);

            // 프로필 생성 후 리다이렉트할 페이지 지정
            return "redirect:/profile/profilelist.do";
        }   // 완료



    // 프로필 로그인
    @GetMapping(value = "/login.do")
    public String loginGET(@RequestParam(name = "nickname", required = false) String nickname,
        Model model, HttpSession session) {
        model.addAttribute("nickname", nickname);
        Profile profile1 = pRepository.findByNickname(nickname);
        if(profile1.getProfilepw() == null){
            session.setAttribute("profileno", profile1.getProfileno());
            session.setAttribute("nickname", nickname);
            return "redirect:/profile/home.do";
        }
        return "/JSH/logintest";
    }
    

    @PostMapping(value = "/login.do")
        public String loginPOST(@RequestParam("nickname") String nickname,
            @RequestParam(value = "profilepw", required = false) String profilepw, Model model, HttpSession session) {
            Profile profile = pRepository.findByNickname(nickname);
            try{
                session.setAttribute("profileno", profile.getProfileno());
                session.setAttribute("nickname", nickname);
                return "redirect:/profile/home.do";
            } catch (Exception e){
                e.printStackTrace();
                return "redirect:profile/login.do";
            }
        }
    


    @GetMapping(value = "/home.do")
        public String showHomePage(HttpSession session) {
            String nickname = (String) session.getAttribute("nickname");
            log.info("nickname => {}",nickname);
            log.info("{}",session.getAttribute("profileno"));
            if (nickname != null) {
                return "/JSH/hometest"; // 로그인된 경우 홈 페이지 경로
            } else {
                return "redirect:/profile/login.do"; // 로그인되지 않은 경우 로그인 폼으로 리다이렉트
            }
        }
}