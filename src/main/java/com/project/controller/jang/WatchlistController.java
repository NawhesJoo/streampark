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

import com.project.dto.Search;
import com.project.entity.Watchlist;
import com.project.mapper.WatchlistMapper;
import com.project.repository.WatchlistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    final String format = "WatchlistController => {}";
    final WatchlistRepository wlRepository;
    final WatchlistMapper wlMapper;
    final BigInteger profileno = BigInteger.valueOf(6);

    @PostMapping(value = "/deletebatch.do")
    public String deleteBatchPOST(@RequestParam(name = "chk[]") List<BigInteger> chk) {
        try {
            // log.info(format,chk.toString());
            wlRepository.deleteAllById(chk);
            return "redirect:/watchlist/selectlist.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/delete.do")
    public String deletePOST(@RequestParam(name = "viewno") BigInteger viewno){
        try {
            // log.info(format,viewno.toString());
            wlRepository.deleteById(viewno);
            return "redirect:/watchlist/selectlist.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 127.0.0.1:9090/streampark/watchlist/selectlist.do
    @GetMapping(value = "/selectlist.do")
    public String selectGET(Model model, @RequestParam(name = "type", defaultValue = "title", required = false) String type, @RequestParam(name = "text", required = false) String text, @ModelAttribute Search obj) {
            try {
                
                log.info(format, type);
                log.info(format, text);
                if(type.equals("title") && text==null) {
                    List<Watchlist> list = wlRepository.findByProfile_profilenoOrderByViewdateDesc(profileno);
                    model.addAttribute("list", list);
                }
                
               
                else if(type.equals("title")) {
                    List<Watchlist> list = wlRepository.findByVideolist_titleIgnoreCaseContainingOrderByViewdateDesc(text, profileno);
                    model.addAttribute("list", list);
                    
                }
                else if(type.equals("pd")) {
                    List<Watchlist> list = wlRepository.findByVideolist_pdIgnoreCaseContainingOrderByViewdateDesc(text, profileno); 
                    model.addAttribute("list", list);
                }
                else if(type.equals("chkage")) {
                    List<Watchlist> list = wlRepository.findByVideolist_chkageIgnoreCaseContainingOrderByViewdateDesc(text, profileno);    
                    model.addAttribute("list", list);
                }

                // model.addAttribute("list", list);
                


                return "/jang/watchlist/selectlist";
        }
        catch(Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Watchlist watchlist) {
        try {
            // log.info(format, watchlist.getProfile().getProfileno());
            // log.info(format, watchlist.getVideolist().getVideocode()); 
            wlRepository.save(watchlist);
            return "redirect:/watchlist/selectlist.do";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }


    @GetMapping(value = "/insert.do")
    public String insertGET() {
        try {
            return "/jang/watchlist/insert";
        }
        catch(Exception e) {
            e.printStackTrace();
            return "redirect:/insert.do";
        }
    }
    
}
