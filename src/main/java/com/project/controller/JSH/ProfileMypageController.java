package com.project.controller.JSH;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Member;
import com.project.entity.Profile;
import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.ProfileRepository;
import com.project.service.JSH.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class ProfileMypageController {

    final ProfileMapper pMapper;
    final ProfileService pService;
    final ProfileRepository pRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    
    // 닉네임 변경
    @GetMapping(value = "/updatenickname.do")
        public String updatenicknameGET(){
            return "/JSH/mypage";
        }

    @PostMapping(value = "/updatenickname.do")
        public String updatenicknamePOST(
            @ModelAttribute("newnickname") String newnickname,
            @RequestParam("profilepw") String profilepw,
            HttpSession session) {

            String nickname = (String) session.getAttribute("nickname");
            try {
            Profile profile1 = pService.findByNickname(nickname);
            profile1.setNickname(newnickname);
            if(profilepw == null){
                pService.updateNickname1(nickname, newnickname);
                pRepository.save(profile1);
                session.removeAttribute("nickname");
                return "redirect:/profile/profilelist.do";
            }
            else if (profilepw != null || !profilepw.isEmpty() || bcpe.matches(profilepw, profile1.getProfilepw())) {
                pService.updateNickname(nickname, newnickname, profilepw);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/profile/profilelist.do";
    }
    

    // 프로필 암호 생성
    @PostMapping(value = "/createpw.do")
    public String coreatepwPOST(@RequestParam("profilepw") String profilepw,
     HttpSession session){
         String nickname = (String) session.getAttribute("nickname");
         log.info("nickname", nickname);
        String memberId = "1";
        Profile profile1 = pRepository.findByNickname(nickname);
        Member member = new Member();
        member.setId(memberId);
        profile1.setMember(member);
        profile1.setNickname(nickname);
        profile1.setProfilepw(bcpe.encode(profilepw));
        pRepository.save(profile1);
        return "redirect:/profile/profilelist.do";
    }



    // 프로필 암호 수정
    @GetMapping(value = "/updatepw.do")
        public String updatepwGET(HttpSession session){
            String nickname = (String) session.getAttribute("nickname");
            Profile profile = pService.findByNickname(nickname);
            if(profile.getProfilepw() == null){
                return "/JSH/createpw";
            }
            return "/JSH/updatepw";
        }

    // @PostMapping(value = "/updatepw.do")
    //     public String updatepwPOST(){
    //         return "/JSH/updatepw";
    //     }



    // 프로필 삭제
    @GetMapping(value = "/delete.do")
    public String deleteGET(HttpSession session){
    String nickname = (String) session.getAttribute("nickname");
    Profile profile = pRepository.findByNickname(nickname);
    if(profile.getProfilepw() != null){
        return "/JSH/delete";
    }
    return "redirect:/mypage/deleteNoPw.do";
    }

    // 암호 있을 때
    @PostMapping(value = "/delete.do")
    public String deletePOST(HttpSession session, Model model,
            @RequestParam("profilepw") String profilepw){
        String nickname = (String) session.getAttribute("nickname");
        Profile profile1 = pRepository.findByNickname(nickname);
        try{
            if(bcpe.encode(profilepw) == profile1.getProfilepw()){
            pMapper.deleteProfile(nickname, profilepw);
            pRepository.save(profile1);
            session.removeAttribute("nickname");
            return "redirect:/profile/profilelist.do";
            }
            if (!bcpe.encode(profilepw).equals(profile1.getProfilepw())) {
                model.addAttribute("error", "암호가 일치하지 않습니다.");
                return "/JSH/delete";
            }
            return "/JSH/delete";
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/profile/delete.do";
        }
    }


    // 암호가 없을 때
    @GetMapping(value ="/deleteNoPw.do")
    public String deleteNoPwGET(HttpSession session){
        session.getAttribute("nickname");
        return "/JSH/deleteNoPw";
    }

    @PostMapping(value = "/deleteNoPw.do")
    public String deleteNoPwPOST(HttpSession session){
        try{
            String nickname = (String) session.getAttribute("nickname");
            Profile profile = pRepository.findByNickname(nickname);
            pMapper.deleteProfileNoPw(nickname);
            pRepository.save(profile);
            session.removeAttribute("nickname");
            return "redirect:/profile/profilelist.do";
        } catch (Exception e){
            e.printStackTrace();
            return "/JSH/delete";
        }
    }

    // 완료


}
