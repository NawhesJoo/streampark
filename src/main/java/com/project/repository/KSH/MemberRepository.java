package com.project.repository.KSH;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Member;
import com.project.entity.MemberProjection;




public interface MemberRepository extends JpaRepository<Member, String>{

    long countById(String id);

    Member findByIdAndPw(String id, String pw);
    Optional<Member> findById(String id);
    MemberProjection findByIdAndName (String id, String name);
    List<MemberProjection> findByPhoneAndName(String phone, String name);


}
