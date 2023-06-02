package com.project.service.JSH;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.project.dto.Profiledto;
import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.entity.Profileimg;

@Service
public interface ProfileService {
    
    // 프로필 생성
    public int createProfile(String keyword, String id, String nickname);

    // 프로필 로그인
    public Profiledto loginProfile(@Param("nickname") String nickname, @Param("profilepw") String profilepw);

    //프로필 정보 가져오기
    public ArrayList<Profile> selectprofile(@Param("id") String id);
    
    // 닉네임으로 프로필 가져오기
    public Profile findByNickname(String nickname);

    // 닉네임 중복확인
    public long countByNickname(String nickname);

    // 닉네임 변경
    public int updateNickname(String nickname, String newNickname, String profilepw);

    // 암호 없는 프로필 닉네임 변경
    public int updateNickname1(String nickname, String newNickname);

    // 프로필 삭제
    public int deleteProfile(String nickname, String profilepw);

    // 암호 없는 프로필 삭제
    public int deleteProfileNoPw(String nickname);

    // 선호 키워드 변경
    public int updateKeyword(String nickname);

    // 프로필 이미지 조회
    public Profileimg findByProfile_profileno(BigInteger profileno);

    // 프로필 이미지 삭제
    public int deleteProfileimg(BigInteger profileno);

    // 프로필 암호 변경
    public int updateProfilepw(String nickname, String newprofilepw);

    // Paychk 유효 불러오기
    public List<Paychk> selectPaychk (String id);

}
