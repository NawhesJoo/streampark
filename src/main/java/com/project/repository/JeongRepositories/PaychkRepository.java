package com.project.repository.JeongRepositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Paychk;

@Repository
public interface PaychkRepository extends JpaRepository<Paychk,BigInteger>{
    
    Paychk findTop1ByOrderByRegdateDesc();
    
}
