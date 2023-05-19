package com.project.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Casts;

public interface CastsRepository extends JpaRepository<Casts, BigInteger>{
    
}
