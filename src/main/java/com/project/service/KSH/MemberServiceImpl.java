package com.project.service.KSH;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.entity.Member;
import com.project.entity.MemberProjection;
import com.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    final MemberRepository mRepository;
    final HttpSession httpSession;
    BigInteger token = new BigInteger("0");
    Date date = new Date();
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @Override
    public Member insertMember(Member obj) {
        obj.setPw(bcpe.encode(obj.getPw()));
        obj.setRole("C");
        obj.setToken(token);
        obj.setRegdate(date);
        return mRepository.save(obj);
    }

    // 로그인
    @Override
    public int login(Member obj) {
        Member obj1 = mRepository.findById(obj.getId()).orElse(null);
        if (bcpe.matches(obj.getPw(), obj1.getPw())) {
            
            httpSession.setAttribute("id", obj.getId());
            httpSession.setAttribute("role", obj.getRole());
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public Member findById(String id) {
        return mRepository.findById(id).get();
    }

    @Override
    public Member updateMember(Member obj1) {
        return mRepository.save(obj1);
    }

    @Override
    public void deleteById(String id) {
        mRepository.deleteById(id);
    }

    @Override
    public void updatePw(String id, String changepw) {
        Member obj1 = mRepository.findById(id).get();
        obj1.setPw(bcpe.encode(changepw));
        mRepository.save(obj1);
    }

    // 정보수정
    @Override
    public Member updateMemberInfo(String id, Member obj) {
        Member obj1 = mRepository.findById(id).get();
        obj1.setName(obj.getName());
        obj1.setEmail(obj.getEmail());
        obj1.setBirth(obj.getBirth());
        obj1.setPhone(obj.getPhone());
        obj1.setGender(obj.getGender());
        mRepository.save(obj1);
        return obj1;
    }

    @Override
    public int updateMemberInfoPw(String id, String pw, String newpw) {
        Member obj1 = mRepository.findById(id).get();
        if (bcpe.matches(pw, obj1.getPw())) {
            obj1.setPw(bcpe.encode(newpw));
            mRepository.save(obj1);
            return 1;
        } else if (!bcpe.matches(pw, obj1.getPw())) {
            return 0;
        } else {
            return 0;
        }
    }

    //
    // 레스터
    // 로그인
    @Override
    public Map<String, Object> loginRest(Member obj1) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("status", 0);
        try {
            Optional<Member> obj = mRepository.findById(obj1.getId());
            if (bcpe.matches(obj1.getPw(), obj.get().getPw())) {
                retMap.put("status", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

    // 아이디 찾기
    @Override
    public Map<String, Object> findidRest(Member obj1) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("list", null);
        retMap.put("status", 0);
        try {
            List<MemberProjection> list = mRepository.findByPhoneAndName(obj1.getPhone(), obj1.getName());
            if (obj1.getEmail().equals(list.get(0).getEmail())) {
                retMap.put("list", list);
                retMap.put("status", 1);
            } else if (list.get(0).getEmail() == null || obj1.getEmail() != list.get(0).getEmail()) {
                retMap.put("list", null);
                retMap.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("list", null);
            retMap.put("status", 0);
        }
        return retMap;
    }

    // 패스워드 찾기
    @Override
    public Map<String, Object> findPwRest(Member obj) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("status", 0);
        try {
            MemberProjection obj1 = mRepository.findByIdAndName(obj.getId(), obj.getName());
            if (obj1.getEmail().equals(obj.getEmail())) {
                retMap.put("status", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

    // 이메일 확인
    @Override
    public Map<String, Object> emailchk(String email) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            long ret = mRepository.countByEmail(email);
            retMap.put("status", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

    // 패스워드 확인
    @Override
    public Map<String, Object> pwcheck(String pw, String id ) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            Member obj = mRepository.findById(id).get();
            if (bcpe.matches(pw, obj.getPw())) {
                retMap.put("status", 1);
            } else {
                retMap.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

    // 아이디 확인
    @Override
    public Map<String, Object> idcheck(String id) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            long ret = mRepository.countById(id);
            retMap.put("status", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", 0);
        }
        return retMap;
    }

}
