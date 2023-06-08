package com.project.controller.jang;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.entity.Review;
import com.project.entity.Watchlist;
import com.project.mapper.ReviewMapper;
import com.project.repository.MemberRepository;
import com.project.repository.ReviewRepository;
import com.project.repository.WatchlistRepository;
import com.project.service.KDH.DHService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/review")
@RequiredArgsConstructor
public class ReviewController {

    final String format = "ReviewController => {}";
    // final BigInteger profileno2 = BigInteger.valueOf(93);
    final HttpSession httpSession;
    final ReviewRepository rRepository;
    final MemberRepository memberRepository;
    final ReviewMapper rMapper;
    final DHService dhService;
    final WatchlistRepository watchlistRepository;

    @PostMapping(value = "/deletebatchmanage.do")
    public String deleteBatchManagePOST(RedirectAttributes redirectAttributes, @RequestParam(name = "videocode", required = false) BigInteger videocode, @RequestParam(name = "chk[]") List<BigInteger> chk) {
        try {
            // log.info(format,chk.toString());
            rRepository.deleteAllById(chk);
            redirectAttributes.addAttribute("videocode", BigInteger.valueOf(videocode.longValue()));
            return "redirect:/review/reviewmanage.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/deletemanage.do")
    public String deletemanagePOST(Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "videocode", required = false) BigInteger videocode, @RequestParam(name = "review_no") BigInteger review_no, @RequestParam(name = "profileno") BigInteger profileno, @AuthenticationPrincipal User user) {
        try {
            String id = user.getUsername();
            String role = memberRepository.findById(id).get().getRole();
            log.info(format, review_no);
            if(role.equals("A")) {
                rRepository.deleteById(review_no);
            }
            redirectAttributes.addAttribute("videocode", BigInteger.valueOf(videocode.longValue()));
            model.addAttribute("videocode", videocode);
            return "redirect:/review/reviewmanage.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/reviewmanage.do")
    public String manageReviewGET(@RequestParam(name = "videocode", required = false) BigInteger videocode, @ModelAttribute Review review, Model model, @AuthenticationPrincipal User user) {
        try {
            String id = user.getUsername();
            String role = memberRepository.findById(id).get().getRole();
            log.info(format, videocode);

            if(videocode == null) {
                List<Review> list = rRepository.findAllByOrderByRegdateDesc();
                model.addAttribute("list", list);
            }
            else {
                List<Review> list = rRepository.findByVideolist_VideocodeIgnoreCaseContainingOrderByViewdateDesc(videocode);
                model.addAttribute("list", list);
                model.addAttribute("role", role);
            }
            model.addAttribute("videocode", videocode);
            return "/jang/review/reviewmanage";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }


    @PostMapping(value = "/deletebatch.do")
    public String deleteBatchPOST(@RequestParam(name = "chk[]") List<BigInteger> chk) {
        try {
            rRepository.deleteAllById(chk);

            // log.info(format,chk.toString());
            // rRepository.deleteAllById(chk);
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
    public String selectvideocodereviewGET(Model model, @RequestParam(name="menu", required = false, defaultValue = "0") int menu, @ModelAttribute Review review, @RequestParam(name = "videocode") BigInteger videocode, @AuthenticationPrincipal User user) {
        try {
            String id = user.getUsername();
            String role = memberRepository.findById(id).get().getRole();
            BigInteger profileno2 = (BigInteger) httpSession.getAttribute("profileno");
            log.info(format, videocode);
            // if(menu == 0) {
            //     return "redirect:/review/selectvideocodereview.do?videocode=1&menu=1";
            // }
            if(menu == 1) {
                List<Review> list = rRepository.findByVideolist_VideocodeIgnoreCaseContainingOrderByViewdateDesc(videocode);
                model.addAttribute("list", list);
            }
            else if(menu == 2) {
                List<Review> list = rRepository.findByVideolist_VideocodeIgnoreCaseContainingOrderByLikesDesc(videocode);
                model.addAttribute("list", list);
            }
            model.addAttribute("videocode", videocode);
            model.addAttribute("profileno", profileno2);
            model.addAttribute("role", role);
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
            BigInteger profileno2 = (BigInteger) httpSession.getAttribute("profileno");
            log.info(format, review_no);
            log.info(format, profileno2);
            Review obj = rRepository.findById(review_no).orElse(null);
            model.addAttribute("obj", obj);
            model.addAttribute("review_no", review_no);
            model.addAttribute("profileno", profileno2);
            return "/jang/review/update";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/delete.do")
    public String deletePOST(@RequestParam(name = "review_no") BigInteger review_no, @RequestParam(name = "profileno") BigInteger profileno,@RequestParam(name = "reviewtitle") String title) {
        try {
            BigInteger profileno2 = (BigInteger) httpSession.getAttribute("profileno");
            log.info(format, review_no);
            log.info(format, profileno);
            log.info(format, profileno2);
            if(profileno.longValue() == profileno2.longValue()) {
                log.info(format, profileno);
                rRepository.deleteById(review_no);
            }
            title = URLEncoder.encode(title, "UTF-8");// redirect 한글깨짐현상 해결
            return "redirect:/kdh/selectone.do?title="+title;
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

    // @GetMapping(value = "/selectlistmine.do")
    // public String selectlistmineGET(@RequestParam(name="menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "videocode", required = false) BigInteger videocode, @ModelAttribute Review review, Model model) {
    //     List<Review> list = rRepository.findByProfilenoOrderByRegdateDesc();
    //     model.addAttribute("list", list);
    //     return "/jang/review/selectlist";
    // }


    // 127.0.0.1:9090/streampark/review/selectlist.do
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(@RequestParam(name="menu", required = false, defaultValue = "0") int menu, @RequestParam(name = "videocode", required = false) BigInteger videocode, @ModelAttribute Review review, Model model, @AuthenticationPrincipal User user) {
        try {
            String id = user.getUsername();
            String role = memberRepository.findById(id).get().getRole();
            log.info(format, role.toString());
            BigInteger profileno2 = (BigInteger) httpSession.getAttribute("profileno");
            if(menu == 0) {
                return "redirect:/review/selectlist.do?menu=1";
                
            }
            if(menu == 1) {
                if(role.equals("C")) {
                    List<Review> list = rRepository.findAllByOrderByRegdateDesc(profileno2);
                    // List<Review> list2 = rRepository.findByProfile_Nickname(profileno2);
                    model.addAttribute("list", list);
                    // model.addAttribute("list", list2);
                }
                else if(role.equals("A")) {
                    List<Review> list = rRepository.findAllByOrderByRegdateDesc();
                    model.addAttribute("list", list);
                }
            }

            else if(menu == 2) {
                if(role.equals("C")) {
                    List<Review> list = rRepository.findAllByOrderByLikesDesc(profileno2);
                    // List<Review> list2 = rRepository.findByProfile_Nickname(profileno2);
                    model.addAttribute("list", list);
                    // model.addAttribute("list", list2);
                }
                else if(role.equals("A")) {
                    List<Review> list = rRepository.findAllByOrderByLikesDesc();
                    model.addAttribute("list", list);
                }
            }

            model.addAttribute("role", role);
            model.addAttribute("profileno", profileno2);
            return "/jang/review/selectlist";

        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 127.0.0.1:9090/streampark/review/insert.do
    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Review review, Model model,@RequestParam(name = "reviewtitle") String title) {
        try {
            BigInteger profileno = (BigInteger)httpSession.getAttribute("profileno");
            BigInteger videocode = dhService.selectnofromtitle(title).getVideocode();
            Watchlist watchlist = new Watchlist();
            Watchlist watchlist2 = watchlistRepository.findByProfile_profilenoAndVideolist_videocode(profileno, videocode);
            BigInteger viewno;
            if(watchlist2 != null){
                viewno = watchlist2.getViewno();
                int ret  = rRepository.countByWatchlist_viewno(viewno);
                watchlist.setViewno(viewno);
                review.setProfileno(profileno);
                review.setWatchlist(watchlist);
                if(viewno != null){
                    if(ret == 0){
                        rRepository.save(review);
                title = URLEncoder.encode(title, "UTF-8");// redirect 한글깨짐현상 해결
                return "redirect:/kdh/selectone.do?title="+title;
                }
                else{
                title = URLEncoder.encode(title, "UTF-8");// redirect 한글깨짐현상 해결
                    return "redirect:/kdh/selectone.do?title="+title;
                }
            }
            else{
                title = URLEncoder.encode(title, "UTF-8");// redirect 한글깨짐현상 해결
                return "redirect:/kdh/selectone.do?title="+title;
            }
            
            }
            else{
                title = URLEncoder.encode(title, "UTF-8");// redirect 한글깨짐현상 해결
                return "redirect:/kdh/selectone.do?title="+title;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/insert.do")
    public String insertGET(@RequestParam(name = "profileno", required = false, defaultValue = "0") BigInteger profileno, @RequestParam(name = "viewno", required = false, defaultValue = "0") BigInteger viewno, Model model) {
        try {
            BigInteger profileno2 = (BigInteger) httpSession.getAttribute("profileno");
            log.info(format, profileno2);
            log.info(format, viewno);
            model.addAttribute("profileno", profileno2);
            model.addAttribute("viewno", viewno);
            return "jang/review/insert";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}
