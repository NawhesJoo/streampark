package com.project.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Profileimg;

@Repository
public interface ProfileimgRepository extends JpaRepository<Profileimg, BigDecimal>{
    
    
}
