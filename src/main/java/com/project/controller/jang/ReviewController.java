package com.project.controller.jang;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Review;
import com.project.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/review")
@RequiredArgsConstructor
public class ReviewController {

    final String format = "ReviewController => {}";
    final BigInteger profileno2 = BigInteger.valueOf(6);
    final ReviewRepository rRepository;


    @PostMapping(value = "/deletebatch.do")
    public String deleteBatchPOST(@RequestParam(name = "chk[]") List<BigInteger> chk) {
        try {
            // log.info(format,chk.toString());
            rRepository.deleteAllById(chk);
            return "redirect:/review/selectlist.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }


    @PostMapping(value = "/selectvideocodereview.do")
    public String selectvideocodereviewPOST() {
        try {
            return "redirect:/review/selectvideocodereview.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "/redirect:/home.do";
        }
    }

    @GetMapping(value = "/selectvideocodereview.do")
    public String selectvideocodereviewGET(Model model, @RequestParam(name="menu", required = false, defaultValue = "1") int menu, @ModelAttribute Review review, @RequestParam(name = "videocode") BigInteger videocode) {
        try {
            log.info(format, videocode);
            if(menu == 1) {
                List<Review> list = rRepository.findByVideolist_VideocodeIgnoreCaseContainingOrderByViewdateDesc(videocode);
                model.addAttribute("list", list);
            }
            else if(menu == 2) {
                List<Review> list = rRepository.findByVideolist_VideocodeIgnoreCaseContainingOrderByLikesDesc(videocode);
                model.addAttribute("list", list);
            }
            model.addAttribute("videocode", videocode);
            return "/jang/review/selectvideocodereview";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/update.do")
    public String updatePOST(@ModelAttribute Review review) {
        try {
            log.info(format, review.toString());
            // 기존 데이터 읽기
            Review review1 = rRepository.findById(review.getReview_no()).orElse(null);
            // 저장하면 자동으로 DB에 변경됨
            review1.setContent(review.getContent());
            return "redirect:/review/selectlist.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/update.do")
    public String updateGET(Model model, @RequestParam(name = "review_no", required = false, defaultValue = "0") BigInteger review_no, @RequestParam(name = "profileno", required = false, defaultValue = "0") BigInteger profileno) {
        try {
            log.info(format, review_no);
            log.info(format, profileno);
            Review obj = rRepository.findById(review_no).orElse(null);
            model.addAttribute("obj", obj);
            model.addAttribute("review_no", review_no);
            model.addAttribute("profileno", profileno);
            return "/jang/review/update";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/delete.do")
    public String deletePOST(@RequestParam(name = "review_no") BigInteger review_no, @RequestParam(name = "profileno") BigInteger profileno) {
        try {
            log.info(format, review_no);
            log.info(format, profileno);
            log.info(format, profileno2);
            if(profileno.longValue() == profileno2.longValue()) {
                log.info(format, profileno);
                rRepository.deleteById(review_no);
            }
            return "redirect:/review/selectlist.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
    // @PostMapping(value = "/selectlist.do")
    // public String selectlistPOST(@RequestParam(name="menu", required = false, defaultValue = "1") int menu) {
    //     try {
    //         if(menu == 1) {
    //             return "redirect:/selectlist.do?menu=1";
    //         }
    //         return "redirect:/selectlist.do";
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/home.do";
    //     }
    // }


    // 127.0.0.1:9090/streampark/review/selectlist.do
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(@RequestParam(name="menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "videocode", required = false) BigInteger videocode, @ModelAttribute Review review, Model model) {
        try {
            if(menu == 0) {
                return "redirect:/review/selectlist.do?menu=1";
                
            }
            if(menu == 1) {
                List<Review> list = rRepository.findAllByOrderByRegdateDesc();
                model.addAttribute("list", list);
            }

            else if(menu == 2) {
                List<Review> list = rRepository.findAllByOrderByLikesDesc();
                model.addAttribute("list", list);
            }
            return "/jang/review/selectlist";

        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 127.0.0.1:9090/streampark/review/insert.do
    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Review review, Model model) {
        try {
            log.info(format, review.toString());
            rRepository.save(review);
            return "redirect:/review/selectlist.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/insert.do")
    public String insertGET(@RequestParam(name = "profileno", required = false, defaultValue = "0") BigInteger profileno, @RequestParam(name = "viewno", required = false, defaultValue = "0") BigInteger viewno, Model model) {
        try {
            log.info(format, profileno);
            log.info(format, viewno);
            model.addAttribute("profileno", profileno);
            model.addAttribute("viewno", viewno);
            return "jang/review/insert";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}
