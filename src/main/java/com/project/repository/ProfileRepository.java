package com.project.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile,BigInteger>{
    
}
