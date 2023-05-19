package com.project.controller.JSH;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.entity.Profile;
import com.project.entity.Profileimg;
import com.project.repository.ProfileRepository;
import com.project.repository.ProfileimgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {

    final String format = "ProfileContrller => {}";
    final ProfileRepository pRepository;
    final ProfileimgRepository piRepository;

    @Value("${default.image}") String DEFAULTIMAGE;
    final ResourceLoader resourceLoader;

    @GetMapping(value = "/create.do")
    public String createJOIN(){
        return "/JSH/join";
    }
    
    @PostMapping(value = "/create.do")
    public String createPOST(@RequestParam("file") MultipartFile file, @RequestParam("memberId") String memberId,
                                @RequestParam("nickname") String nickname, @RequestParam("keyword") String keyword,
                                HttpSession session) {
        // 세션에서 현재 사용자의 아이디 가져오기
        String sessionId = (String) session.getAttribute("memberId");

        if (!sessionId.equals(memberId)) {
            // 현재 사용자와 입력한 아이디가 일치하지 않을 경우 처리
            // 예: 에러 메시지 출력, 리다이렉트 등
            return "/JSH/join";
        }

        try {
            // 프로필 생성 처리

            // 프로필 이미지 파일 저장
            String filename = file.getOriginalFilename();
            byte[] filedata = file.getBytes();

            // 프로필 엔티티 생성
            Profile profile = new Profile();
            profile.setNickname(nickname);
            profile.setKeyword(keyword);

            // 프로필 이미지 엔티티 생성 및 연결
            Profileimg profileimg = new Profileimg();
            profileimg.setFilename(filename);
            profileimg.setFiledata(filedata);
            profileimg.setFiletype(file.getContentType());
            profileimg.setFilesize(BigInteger.valueOf(file.getSize()));

            profileimg.setProfile(profile);
            profile.setProfileimg(profileimg);

            // 프로필 저장
            pRepository.save(profile);

        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
        }

        return "/JSH/join";
    }

    // ...
}