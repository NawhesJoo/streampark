package com.project.repository.JangRepositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, BigInteger> {
    
}
