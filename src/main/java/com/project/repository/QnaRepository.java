package com.project.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Board;

public interface QnaRepository extends JpaRepository<Board, BigInteger>{

    // 문의글 전체 목록
    List<Board> findAllByOrderByNoDesc();
}
