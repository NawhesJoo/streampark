package com.project.controller.jang;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.dto.Search;
import com.project.entity.Watchlist;
import com.project.repository.JangRepositories.WatchlistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    final String format = "WatchlistController => {}";
    final WatchlistRepository wlRepository;
    final BigInteger profileno = BigInteger.valueOf(87);

    @PostMapping(value = "/deletebatch.do")
    public String deleteBatchPOST(@RequestParam(name = "chk[]") List<BigInteger> chk) {
        try {
            log.info(format,chk.toString());
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
            log.info(format,viewno.toString());
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
    public String selectGET(Model model, @ModelAttribute Search obj) {
        try {
            // 페이지네이션
            if(obj.getPage() == 0) {
                return "redirect:/watchlist/selectlist.do?page=1&type=title&text=";
            }
            PageRequest pageRequest = PageRequest.of((obj.getPage()-1), 10);

            // 제목 검색
            List<Watchlist> list2 = wlRepository.findByVideolist_titleIgnoreCaseContainingOrderByViewdateDesc(obj.getText(), pageRequest);
            long total = wlRepository.countByVideolist_titleContaining(obj.getText());

            // PD 검색
            if(obj.getType().equals("pd")) {
                list2 = wlRepository.findByVideolist_pdIgnoreCaseContainingOrderByViewdateDesc(obj.getText(), pageRequest);
                total = wlRepository.countByVideolist_pdContaining(obj.getText());
            }
            else if(obj.getType().equals("chkage")) {
                list2 = wlRepository.findByVideolist_chkageIgnoreCaseContainingOrderByViewdateDesc(obj.getText(), pageRequest);
                total = wlRepository.countByVideolist_chkageContaining(obj.getText());
            }
            // log.info(format, profileno);
            log.info(format, obj.toString());
            List<Watchlist> list = wlRepository.findByProfile_profilenoOrderByViewdateDesc(profileno);
            model.addAttribute("list", list);
            model.addAttribute("list2", list2);
            model.addAttribute("search", obj);
            model.addAttribute("pages", (total-1) + 1 / 10);

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
            log.info(format, watchlist.getProfile().getProfileno());
            log.info(format, watchlist.getVideolist().getVideocode()); 
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
