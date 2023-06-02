package com.project.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Paymentlist;

public interface PaymentlistRepository extends JpaRepository<Paymentlist, BigInteger>{
    
}
