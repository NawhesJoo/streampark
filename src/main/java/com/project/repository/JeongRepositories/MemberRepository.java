package com.project.repository.JeongRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Member;
import com.project.repository.JeongRepositories.Projections.MemberProjection;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {

    MemberProjection findDistinctById(String id);
    
}
