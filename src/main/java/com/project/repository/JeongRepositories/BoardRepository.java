package com.project.repository.JeongRepositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Board;

public interface BoardRepository extends JpaRepository<Board,BigInteger>{
    
}
