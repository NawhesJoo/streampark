package com.project.controller.JSH;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.entity.Profileimg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileImgController {

    
    
    @PostMapping(value = "/insertimage.do")
public String insertImagePOST(@ModelAttribute Profileimg profileimg,
    @RequestParam(name="tmpfile") MultipartFile file){
    try{
        profileimg.setFiledata(file.getInputStream().readAllBytes());
        profileimg.setFilesize(BigInteger.valueOf(file.getSize()));
        profileimg.setFiletype(file.getContentType());
        profileimg.setFilename(file.getOriginalFilename());
        // log.info(format, image1.toString());
        // bi1Repository.save(profileimg);
        return "redirect:/boardimage1/selectlist.do?no=" + profileimg.getProfile().getProfileno();
    }
    catch(Exception e){
        e.printStackTrace();
        return "redirect:/home.do";
    }
}
}
