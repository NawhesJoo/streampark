package com.project.repository.JeongRepositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Fee;

public interface FeeRepository extends JpaRepository<Fee,BigInteger>{
    
}
