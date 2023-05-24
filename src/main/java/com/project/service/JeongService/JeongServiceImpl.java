package com.project.service.JeongService;

import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.project.entity.Fee;
import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.repository.JeongRepositories.FeeRepository;
import com.project.repository.JeongRepositories.MemberRepository;
import com.project.repository.JeongRepositories.PaychkRepository;
import com.project.repository.JeongRepositories.ProfileRepository;
import com.project.repository.JeongRepositories.Projections.MemberProjection;

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
    // proRepository.findById(BigInteger.valueOf(88)).orElse(null);

    @Override
    public int insertPaychkMembership(Paychk obj) {
        try {
            String id = (String) httpSession.getAttribute("id");
            Member member = memRepository.findById(id).orElse(null);
            Fee fee = feeRepository.findById(obj.getFee().getGrade()).orElse(null);            
            long token = member.getToken().longValue() - ((fee.getPrice().longValue()/100) - (obj.getPrice().longValue()/100));
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

}
