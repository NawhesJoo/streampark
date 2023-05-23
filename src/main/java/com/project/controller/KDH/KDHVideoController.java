package com.project.controller.KDH;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

import com.project.dto.Actorsdto;
import com.project.dto.Videoimgdto;
import com.project.dto.VideolistView;
import com.project.dto.Videolistdto;
import com.project.entity.Member;
import com.project.entity.Videoimg;
import com.project.entity.Videolist;
import com.project.repository.KDH.VideoimgRepository;
import com.project.repository.KDH.videolistRepository;
import com.project.service.KDH.DHService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/kdh")
@Slf4j
public class KDHVideoController {
    final videolistRepository videolistRepository;
    final VideoimgRepository videoimgRepository;
    final DHService dhService;
    final ResourceLoader resourceLoader; // resource폴더의 파일을 읽기위한 객체 생성
    @Value("${default.image}")
    String defaultImage;
    @GetMapping(value = "/error.do")
    public String errorGET() {
        return "/KDH/error";
    }

    @GetMapping(value = "/videoinsert.do")
    public String videoinsertGET() {
        try {
            return "/KDH/StreamPark_insertvideo";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }

    @PostMapping(value = "/videoinsert.do")
    public String videoinsertPOST(@ModelAttribute Videolistdto videolist) {
        try {
            Member member = new Member(); // 멤버를 받기위해 사용 통합후 삭제 및 수정
            member.setRole("a");
            log.info("{}", videolist.toString());
            log.info("{}회", videolist.getEpisode().intValue());
            dhService.videolistInsert(member, videolist);
            return "redirect:/kdh/manageactor.do?title="+videolist.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }

    @GetMapping(value = "/videoupdate.do")
    public String videoupdateGET(@RequestParam(name = "title") String title, Model model) {
        try {
            // BigInteger videocode = dhService.selectnofromtitle(title).getVideocode();
            // VideolistView video=dhService.selectvideoOne(videocode);
           Videolist video = dhService.selectnofromtitle(title);
            model.addAttribute("video", video);
            return "/KDH/StreamPark_updatevideo";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }

    @PostMapping(value = "/videoupdate.do")
    public String videoupdatePOST(Model model,@ModelAttribute Videolistdto videolist, @RequestParam(name = "title") String title,@RequestParam(name="nowtitle") String nowtitle) {
        try {
            Member member = new Member(); // 멤버를 받기위해 사용 통합후 삭제 및 수정
            member.setRole("a");
            dhService.videolistUpdate(member, videolist, nowtitle);
            return "redirect:/kdh/selectone.do?title="+videolist.getTitle();
            // return "redirect:/kdh/home.do";
                } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }

    @GetMapping(value = "/home.do")
    public String homeGET(Model model) {
        List<Videolistdto> list = dhService.selectvideolist();
        for(Videolistdto obj :list){
            System.out.println(obj.getVideocode());
           Long imgno =dhService.selectvideoimgOne((obj.getVideocode()));
            obj.setImgno(imgno);
        }
        model.addAttribute("list", list);
        return "/KDH/StreamPark_home";
    }

    @GetMapping(value = "/selectone.do")
    public String selectoneGET(@RequestParam(name = "title") String title, Model model) {
        BigInteger videocode = dhService.selectnofromtitle(title).getVideocode();
        VideolistView video = dhService.selectvideoOne(videocode);
        model.addAttribute("video", video);
        return "/KDH/StreamPark_selectone";
    }
    @GetMapping(value = "/manageactor.do")
    public String manageactorGET(@RequestParam(name = "title") String title, Model model) {
        try {
            BigInteger videocode = dhService.selectnofromtitle(title).getVideocode();
            List<Actorsdto> actorlist = dhService.selectactors();
            List<Long> actorsinvideolist= dhService.selectactorsinvideo(videocode);
            List<Actorsdto> list = new ArrayList<>();
            for(Long no :actorsinvideolist){
                // System.out.println(dhService.selectnotoname(no));
                list.add(dhService.selectnotoname(no));

            }
            // System.out.println(title);
            // System.out.println(videocode);
            // System.out.println(actorsinvideolist);
            
            // System.out.println(list);
            model.addAttribute("title", title);
            model.addAttribute("list", list);
            model.addAttribute("actorlist", actorlist);
            return "/KDH/StreamPark_manageactor";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }
    @PostMapping(value = "/castsinsert.do")
    public String castsinsertPOST(@RequestParam(name="title") String title, @RequestParam(name = "chk[]") String[] chk){
        try {
            System.out.println(title);
            BigInteger videocode = dhService.selectnofromtitle(title).getVideocode();
            System.out.println(chk.length);
            for(int i=0; i<chk.length; i++){
                Videolistdto videolist =new Videolistdto();
                Actorsdto actors = new Actorsdto();
                videolist.setVideocode(videocode.longValue());
                actors.setActors_No(Long.parseLong(chk[i]));
                System.out.println(chk[i]);
                System.out.println(videocode);
               BigInteger epi= videolistRepository.countByTitle(title);
                if(dhService.castsInsertactorchk(Long.parseLong(chk[i]), videocode.longValue())<=0){
                    for(int j=0; j<epi.intValue(); j++){
                        videolist.setVideocode(videocode.longValue()+j);
                        dhService.addactorinvideo(videolist, actors);
                    }
                }
            }
            return "redirect:/kdh/manageactor.do?title="+title;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
            }
    }
    @PostMapping(value = "/actordelete.do")
    public String actordeletePOST(@RequestParam(name="title") String title, @RequestParam(name = "chk[]1") String[] chk){
        try {
            BigInteger videocode = dhService.selectnofromtitle(title).getVideocode();
            for(int i=0; i<chk.length; i++){
                Videolistdto videolist =new Videolistdto();
                Actorsdto actors = new Actorsdto();
                videolist.setVideocode(videocode.longValue());
                actors.setActors_No(Long.parseLong(chk[i]));
                dhService.removeactorinvideo(videolist, actors);
            }
            return "redirect:/kdh/manageactor.do?title="+title;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
            }
    }
    @PostMapping(value = "/actorlistinsert.do")
    public String actorlistinsertPOST(@RequestParam(name="title") String title, @RequestParam(name = "actorname") String actorname){
        try {
            int ret =dhService.addactorlist(actorname);
            if(ret==1){
                return "redirect:/kdh/manageactor.do?title="+title;
            }
            else{
                return "redirect:/kdh/error.do";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
    }
    @PostMapping(value="/videodelete.do")
    public String videodeletePOST(@RequestParam(name="title") String title){
        try {
            List<Videolist> list = dhService.selectvideofordelete(title);
            for(Videolist no : list ){
                videolistRepository.deleteById(no.getVideocode());
            }
            return "redirect:/kdh/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/kdh/error.do";
        }
        
    }
    

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") BigInteger no) throws IOException {

       Videoimg obj = videoimgRepository.findById(no).orElse(null);
        HttpHeaders headers = new HttpHeaders();
        if (obj != null) {
            if (obj.getFilesize().longValue() > 0) {
                headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
                return new ResponseEntity<>(obj.getFiledata(), headers, HttpStatus.OK);

            }
        }
        // InputStream is =
        // resourceLoader.getResource("classpath:/static/images/default.png").getInputStream();
        InputStream is = resourceLoader.getResource(defaultImage).getInputStream();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }
}
