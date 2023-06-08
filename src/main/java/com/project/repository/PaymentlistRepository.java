package com.project.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Paymentlist;

public interface PaymentlistRepository extends JpaRepository<Paymentlist, BigInteger>{

    List<Paymentlist> findByProfile_profileno(BigInteger profileno);
    
}
