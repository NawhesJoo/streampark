package com.project.service.KSH;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.entity.Member;

@Service
public interface MemberService {
    
    // 회원가입
    public Member insertMember(Member obj);
    
    // 회원수정
    public Member updateMember(Member obj);

    // 로그인
    public int login(Member obj);

    // 아이디 찾기
    public Member findById(String id);

    // 아이디 삭제
    public void deleteById(String id);

    // 비밀번호 찾기
    public void updatePw(String id, String pw);

    // 정보수정
    public Member updateMemberInfo(String id, Member obj);
    public int updateMemberInfoPw(String id,  String pw, String newpw);
    // 래스터

    public Map<String,Object> loginRest(Member obj1);
 
    public Map<String,Object> findidRest(Member obj1);

    public Map<String,Object> findPwRest(Member obj);

    public Map<String,Object> emailchk(String email);

    public Map<String,Object> pwcheck(String pw, String id);

    public Map<String,Object> idcheck(String id);
}
