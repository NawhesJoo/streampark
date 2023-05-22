package com.project.controller.JSH;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.entity.Profileimg;
import com.project.repository.ProfileimgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profileimg")
@RequiredArgsConstructor
public class ProfileImgController {

    final ProfileimgRepository piRepository;

    @GetMapping(value ="/insertimage.do")
    public String insertImageGET(){
    return "/JSH/imgtest";
    }

    @PostMapping(value = "/insertimage.do")
    public String insertImagePOST(@ModelAttribute Profileimg profileimg,
        @RequestParam(name="tmpfile") MultipartFile file){
        try{
            profileimg.setFilesize(BigInteger.valueOf(file.getSize()));
            profileimg.setFiledata(file.getInputStream().readAllBytes());
            profileimg.setFiletype(file.getContentType());
            profileimg.setFilename(file.getOriginalFilename());
            // log.info(format, image1.toString());
            log.info("profileimg => {}", profileimg.toString());
            piRepository.save(profileimg);
            
            return "/JSH/imgtest";
        }
        catch(Exception e){
            e.printStackTrace();
            return "/JSH/imgtest";
        }
    }
}