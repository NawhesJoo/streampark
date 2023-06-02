package com.project.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Interestlist;

@Repository
public interface InterestRepository  extends JpaRepository<Interestlist,BigInteger>{
    
    List<Interestlist> findByProfile_profileno(BigInteger profileno);
    Interestlist findByProfile_profilenoAndVideolist_videocode( BigInteger profileno,BigInteger videocode);
}
