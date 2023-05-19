package com.project.repository.msh;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Board;

public interface QnARepository extends JpaRepository<Board, BigInteger>{

    public List<Board> findAllByOrderByNoDesc();
 
    
}
