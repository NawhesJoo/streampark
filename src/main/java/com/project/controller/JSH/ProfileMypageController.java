package com.project.controller.JSH;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.ProfileRepository;
import com.project.service.JSH.ProfileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class ProfileMypageController {
    final ProfileService profileService;
    final ProfileMapper pMapper;
    final ProfileRepository pRepository;
    final HttpSession httpSession;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    // // 닉네임 변경
    // @GetMapping(value = "/updatenickname.do")
    // public String updatenicknameGET(@RequestParam(name = "nickname") String nickname){
    //     return "/JSH/StreamPark_mypage";
    // }

    // @PostMapping(value = "/updatenickname.do")
    // public String updatenicknamePOST(@ModelAttribute Profile profile, @RequestParam("profilepw") String profilepw){
    //     try{
    //     Profiledto profile1 = pMapper.findByNickname(profile.getNickname());

    //     if(bcpe.matches(profilepw, profile1.getProfilepw())){
    //         pMapper.updateNickname(profilepw);
    //     } else{

    //     }
    //     } catch (Exception e){
    //         e.printStackTrace();
    //         return "/JSH/StreamPark_mypage";
    //     }
    //     return "/JSH/StreamPark_mypage";
    // }
    

    // 프로필 삭제

}
