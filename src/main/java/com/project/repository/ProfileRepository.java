package com.project.repository;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, BigInteger>{

    // 프로필 리스트
    ArrayList<Profile> findByMember_id(String id); // Member의 id이므로 Member_id, profile

    // 닉네임으로 프로필 가져오기
    Profile findByNickname(String nickname);

    // 닉네임 중복확인
    long countByNickname(String nickname);

    // 프로필 갯수 확인
    long countByMember_id(String id);

    // 프로필 삭제
    Profile deleteByNickname(String nickname);
}
