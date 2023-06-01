package com.project.controller.JSH;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Value("${default.profileimg}")
    private String defaultprofileimg;
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
        InputStream is = resourceLoader.getResource(defaultprofileimg).getInputStream(); // exception 발생됨.
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>( is.readAllBytes(), headers, HttpStatus.OK);
    }

    @GetMapping(value ="/insertimage.do")
    public String insertImageGET(){
    return "/JSH/profileimg";
    }

    @PostMapping(value = "/insertimage.do")
    public String insertImagePOST(@ModelAttribute Profileimg profileimg,
        @RequestParam(name="tmpfile") MultipartFile file, Model model){
        try{
            profileimg.setFilesize(BigInteger.valueOf(file.getSize()));
            profileimg.setFiledata(file.getInputStream().readAllBytes());
            profileimg.setFiletype(file.getContentType());
            profileimg.setFilename(file.getOriginalFilename());
            // log.info(format, image1.toString());
            log.info("profileimg => {}", profileimg.toString());
            piRepository.save(profileimg);
            model.addAttribute("chk", profileimg.getFilename());
            return "/JSH/profileimg";
        }
        catch(Exception e){
            e.printStackTrace();
            return "/JSH/profileimg";
        }
    }
}