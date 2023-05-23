package com.project.service.JSH;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.project.dto.Profiledto;
import com.project.entity.Profile;

@Service
public interface ProfileService {
    
    // 프로필 생성
    public int createProfile(String keyword, String id, String nickname);

    // 프로필 로그인
    public Profiledto loginProfile(@Param("nickname") String nickname, @Param("profilepw") String profilepw);

    //프로필 정보 가져오기
    public List<Profile> selectprofile(@Param("id") String id);
    
    // 닉네임으로 프로필 가져오기
    public Profile findByNickname(String nickname);

    // 닉네임 중복확인
    public long countByNickname(String nickname);

    // 닉네임 변경
    public int updateNickname(String nickname, String newNickname, String profilepw);

}
