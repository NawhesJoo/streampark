package com.project.repository.KSH;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Member;




public interface MemberRepository extends JpaRepository<Member, String>{

    long countById(String id);

    Member findByIdAndPw(String id, String pw);


}
