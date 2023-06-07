package com.project.service.JeongService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.project.dto.Jeong.BestList;
import com.project.dto.Jeong.PaymentVideolist;
import com.project.entity.Fee;
import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.entity.Videolist;
import com.project.mapper.Jeong.BestMapper;
import com.project.repository.FeeRepository;
import com.project.repository.MemberRepository;
import com.project.repository.PaychkRepository;
import com.project.repository.ProfileRepository;
import com.project.repository.WatchlistRepository;
import com.project.repository.videolistRepository;
import com.project.repository.Projections.MemberProjection;
import com.project.service.KDH.DHService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class JeongServiceImpl implements JeongService {

    final HttpSession httpSession;
    final ProfileRepository proRepository;
    final MemberRepository memRepository;
    final PaychkRepository payRepository;
    final FeeRepository feeRepository;    
    final BestMapper bestMapper;
    final WatchlistRepository watRepository;
    final videolistRepository vidRepository;
    final DHService dhService;
    // proRepository.findById(BigInteger.valueOf(88)).orElse(null);

    @Override
    public int insertPaychkMembership(Paychk obj) {
        //멤버쉽 등록
        try {
            String id = (String) httpSession.getAttribute("id");
            Member member = memRepository.findById(id).orElse(null);
            Fee fee = feeRepository.findById(obj.getFee().getGrade()).orElse(null);            
            long token = member.getToken().longValue() - ((fee.getPrice().longValue()/100) - (obj.getPrice().longValue()/100));
            member.setMembershipchk(obj.getFee().getGrade());
            member.setToken(BigInteger.valueOf(token));
            log.info("obj ->{}", obj.toString());
            payRepository.save(obj);
            memRepository.save(member);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Profile findProfileById(long profileno) {
        try {
            return proRepository.findById(BigInteger.valueOf(profileno)).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MemberProjection findMemberById(String id) {
        try {
            return memRepository.findDistinctById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Paychk findPaychkTopByRegdate() {
        try {
            return payRepository.findTop1ByOrderByRegdateDesc();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insertPaychkToken(Paychk obj) {
        try {
            String id = (String) httpSession.getAttribute("id");
            Member member = memRepository.findById(id).orElse(null);

            long token = member.getToken().longValue() + Long.parseLong(obj.getChargetoken().getToken());

            member.setToken(BigInteger.valueOf(token));

            payRepository.save(obj);
            memRepository.save(member);

            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public Paychk findPaychkMemberidAndTypeTopByRegdate(String id, String type) {
        try {
            return payRepository.findTop1ByMember_idAndTypeOrderByRegdateDesc(id, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Fee findFeeById(BigInteger grade) {
        try {
            return feeRepository.findById(grade).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Fee> findFeeAll() {
        try {                        
            return feeRepository.findAll(Sort.by(Sort.Direction.ASC,"grade"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateMembership(Member member) {
        try {
            Member obj = memRepository.findById(member.getId()).orElse(null);
            obj.setMembershipchk(member.getMembershipchk());
            log.info("updatemembership ->{}", obj);
            memRepository.save(obj);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<BestList> selectTop5Watchlist() {
        try {
            return bestMapper.selectTop5Watchlist();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Videolist> findTop5Videolist(List<BestList> bestLists) {
        try {
            List<BigInteger> videoCodeList = new ArrayList<>();
            // List<Videolistdto> videolistdtos = new ArrayList<>();
            for(BestList obj : bestLists){
                videoCodeList.add(obj.getVideocode());
            }

            List<Videolist> videolists = vidRepository.findAllById(videoCodeList);

            for(Videolist obj : videolists){                
                obj.setImgno(dhService.selectvideoimgOne(obj.getVideocode().longValue()));
            }
            return videolists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BestList> selectTop5Review() {
        try {
            return bestMapper.selectTop5Review();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    @Override
    public List<PaymentVideolist> paymenstlistToPaymentVideolist(List<com.project.entity.Paymentlist> paymentlists) {
        try {
            List<PaymentVideolist> list = new ArrayList<>();
            for(com.project.entity.Paymentlist obj : paymentlists){
                PaymentVideolist one = new PaymentVideolist();
                one.setPaymentlistno(obj.getPaymentlistno());
                one.setProfileno(obj.getProfile().getProfileno());
                one.setRegdate(obj.getRegdate());              
                one.setVideocode(obj.getVideolist().getVideocode());              
                list.add(one);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PaymentVideolist> getVideoTitle(List<PaymentVideolist> paymentVideolists) {
        try {
            List<PaymentVideolist> list = new ArrayList<>();
            for(PaymentVideolist obj: paymentVideolists){      
                Videolist vlist = vidRepository.findById(obj.getVideocode()).orElse(null);  
                // log.info("getvideotitle {}",vlist);        
                obj.setTitle(vlist.getTitle());
                obj.setImgno(vlist.getVideoimgs().get(0).getNo());
                list.add(obj);
            }            
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    

}
