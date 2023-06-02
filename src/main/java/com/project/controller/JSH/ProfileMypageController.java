package com.project.controller.JSH;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

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
import org.springframework.web.multipart.MultipartFile;

import com.project.entity.Member;
import com.project.entity.Profile;
import com.project.entity.Profileimg;
import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.ProfileRepository;
import com.project.repository.ProfileimgRepository;
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
    final ProfileimgRepository piRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    @Value("${default.image}") String DEFAULTIMAGE;
    final ResourceLoader resourceLoader;

    // 마이페이지
    @GetMapping(value ="/mypage.do")
        public String mypageGET(HttpSession session){
            session.getAttribute("profileno");
            session.getAttribute("nickname");
            session.getAttribute("id");
            
            String nickname = (String) session.getAttribute("nickname");
            Profile profile = pRepository.findByNickname(nickname);
            if(profile.getProfilepw() != null){
                return "/JSH/mypage";
            }
            return "redirect:/mypage/updatenickname.do";
        }

    @PostMapping(value ="/mypage.do")    
        public String mypagePOST(HttpSession session,
        @RequestParam(value = "profilepw", required = false) String profilepw){
            String nickname = (String) session.getAttribute("nickname");
            Profile profile = pRepository.findByNickname(nickname);
            session.setAttribute("profileno", profile.getProfileno());
            session.setAttribute("nickname", nickname);
            return "redirect:/mypage/updatenickname.do";
        }
    
    // 닉네임 변경
    @GetMapping(value = "/updatenickname.do")
        public String updatenicknameGET(HttpSession session, Model model){
            String nickname = (String) session.getAttribute("nickname");
            Profile profile = pRepository.findByNickname(nickname);
            log.info("nickname => {}", nickname);
            if (profile.getProfilepw() != null){
                model.addAttribute("profilepwchk", true);
            }
            return "/JSH/profilenickname";
        }

    @PostMapping(value = "/updatenickname.do")
        public String updatenicknamePOST(
            @RequestParam("newnickname") String newnickname,
            HttpSession session) {

            log.info("newnickname => {}", newnickname);
            String nickname = (String) session.getAttribute("nickname");
                
            Profile profile = pRepository.findByNickname(nickname);
            log.info("Profile => {}", profile.toString());
        try {
            profile.setNickname(newnickname);
            pRepository.save(profile);
            session.setAttribute("nickname", newnickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/profile/profilelist.do";
    }
    

    // 프로필 암호 수정
    @GetMapping(value = "/updatepw.do")
    public String updatepwGET(HttpSession session){
        String nickname = (String) session.getAttribute("nickname");
        Profile profile = pService.findByNickname(nickname);
        if(profile.getProfilepw() == null){
            return "/JSH/profilecreatepw";
        }
        return "/JSH/updatepw";
    }

    @PostMapping(value = "/updatepw.do")
    public String updatepwPOST(HttpSession session,
        @RequestParam("profilepw") String profilepw,
        @ModelAttribute("newprofilepw") String newprofilepw){
        String nickname = (String) session.getAttribute("nickname");
        try{
            Profile profile = pRepository.findByNickname(nickname);
            profile.setProfilepw(bcpe.encode(newprofilepw));
            pRepository.save(profile);
            return "redirect:/mypage/updatenickname.do";
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/mypage/updatepw.do";
        }
    }



    // 프로필 암호 생성
    @PostMapping(value = "/createpw.do")
    public String coreatepwPOST(@RequestParam("profilepw") String profilepw,
     HttpSession session){
         String nickname = (String) session.getAttribute("nickname");
         log.info("nickname", nickname);
        // String memberId = "1";
        String memberId = (String) session.getAttribute("id");
        Profile profile1 = pRepository.findByNickname(nickname);
        Member member = new Member();
        member.setId(memberId);
        profile1.setMember(member);
        profile1.setNickname(nickname);
        profile1.setProfilepw(bcpe.encode(profilepw));
        pRepository.save(profile1);
        return "redirect:/profile/profilelist.do";
    }

    // 프로필 암호 삭제
    @PostMapping(value = "/deletepw.do")
    public String deletepwPOST(@RequestParam("deletepw") String deletepw,
    HttpSession session){
        String nickname = (String) session.getAttribute("nickname");
        Profile profile = pRepository.findByNickname(nickname);
        profile.setProfilepw("");
        pRepository.save(profile);
        return "redirect:/mypage/updatepw.do";
    }


    // 프로필 삭제
    @GetMapping(value = "/delete.do")
    public String deleteGET(HttpSession session, Model model){
    String nickname = (String) session.getAttribute("nickname");
    Profile profile = pRepository.findByNickname(nickname);
    if (profile.getProfilepw() != null){
        model.addAttribute("profilepwchk", true);
    }
    return "/JSH/profiledelete";
    }


    @PostMapping(value = "/delete.do")
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
            return "/JSH/profiledelete";
        }
    }   // 완료


    // 선호 키워드 변경
    @GetMapping(value = "/updatekeyword.do")
    public String updatekeywordGET(HttpSession session, Model model){
        String nickname = (String) session.getAttribute("nickname");
        Profile profile = pRepository.findByNickname(nickname);
        model.addAttribute("keyword", profile.getKeyword());
        return "/JSH/updatekeyword";
    }

    @PostMapping(value = "/updatekeyword.do")
    public String updatekeywordPOST(@ModelAttribute("profile") Profile profile,
        @RequestParam("keyword") String keyword,
        HttpSession session){
            String nickname = (String) session.getAttribute("nickname");
            Profile profile1 = pRepository.findByNickname(nickname);
            profile1.setKeyword(keyword);
            pRepository.save(profile1);
            return "redirect:/mypage/updatenickname.do";
    } // 완료


    // 프로필 이미지

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

    // 프로필 이미지 추가 및 수정
    @GetMapping(value = "/profileimg.do")
    public String profileimgGET(HttpSession session, Model model){
        model.addAttribute("profileno", session.getAttribute("profileno")) ;
        log.info("session => {}", session.getAttribute("profileno"));
        return "/JSH/profileimg";
    }


    @PostMapping(value = "/profileimg.do")
    public String profileimgPOST(@RequestParam(name="tmpfile") MultipartFile file, HttpSession session){

        BigInteger profileno = (BigInteger) session.getAttribute("profileno");
        Profileimg profileimg1 = piRepository.findByProfile_Profileno(profileno);
        try{
            if( file.isEmpty()){
                return "redirect:/mypage/profileimg.do";
            }
            if(profileimg1 != null){
            profileimg1.setFilesize( BigInteger.valueOf(file.getSize()) );
            profileimg1.setFiledata( file.getInputStream().readAllBytes() );
            profileimg1.setFiletype( file.getContentType() );
            profileimg1.setFilename( file.getOriginalFilename() );

            piRepository.save(profileimg1);
            return "redirect:/mypage/profileimg.do";
            }
                Profileimg profileimg = new Profileimg();
                Profile profile = new Profile();

                profileimg.setFilesize( BigInteger.valueOf(file.getSize()) );
                profileimg.setFiledata( file.getInputStream().readAllBytes() );
                profileimg.setFiletype( file.getContentType() );
                profileimg.setFilename( file.getOriginalFilename() );
                
                profile.setProfileno(profileno);
                profileimg.setProfile(profile);
                piRepository.save(profileimg);
                return "redirect:/mypage/profileimg.do";
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/mypage/profileimg.do";
        }
    } // 완료



    // 프로필 이미지 삭제
    @PostMapping(value = "/deleteprofileimg.do")
    public String deleteprofileimgPOST(HttpSession session){
        BigInteger profileno = (BigInteger) session.getAttribute("profileno");
        Profileimg profileimg = piRepository.findByProfile_Profileno(profileno);
        try{
            if(profileimg != null){
            pService.deleteProfileimg(profileno);
            return "redirect:/mypage/profileimg.do";
            }
            return "redirect:/mypage/profileimg.do";
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:/mypage/profileimg.do";
        }
    }

}
