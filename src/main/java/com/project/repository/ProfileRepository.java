package com.project.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, BigDecimal>{

    // 프로필 리스트
    List<Profile> findByMember_id(String id); // Member의 id이므로 Member_id, profile

    // 프로필 가져오기
    Profile findByNickname(String nickname);

    // 닉네임 중복확인
    long countByNickname(String nickname);
}
