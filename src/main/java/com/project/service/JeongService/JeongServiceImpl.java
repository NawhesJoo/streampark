package com.project.service.JeongService;

import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.project.entity.Member;
import com.project.entity.Paychk;
import com.project.entity.Profile;
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
    // proRepository.findById(BigInteger.valueOf(88)).orElse(null);

    @Override
    public int insertPaychkMembership(Paychk obj) {
        try {
            payRepository.save(obj);
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

}
