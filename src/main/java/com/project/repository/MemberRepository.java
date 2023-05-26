package com.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Member;
import com.project.repository.Projections.MemberProjection;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {

    long countById(String id);

    Member findByIdAndPw(String id, String pw);
    Optional<Member> findById(String id);
    com.project.entity.MemberProjection findByIdAndName (String id, String name);
    List<com.project.entity.MemberProjection> findByPhoneAndName(String phone, String name);
    
    MemberProjection findDistinctById(String id);
    
}
