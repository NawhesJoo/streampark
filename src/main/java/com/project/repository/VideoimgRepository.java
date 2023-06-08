package com.project.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Videoimg;

@Repository
public interface VideoimgRepository extends JpaRepository<Videoimg, BigInteger>{

    
    
}
