package com.project.repository.KDH;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Videolist;

@Repository
public interface videolistRepository extends JpaRepository<Videolist, BigInteger>{
    
}
