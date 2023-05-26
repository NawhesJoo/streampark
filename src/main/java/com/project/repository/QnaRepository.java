package com.project.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Board;

@Repository
public interface QnaRepository extends JpaRepository<Board, BigInteger>{

    // 문의글 전체 목록
    List<Board> findAllByOrderByNoDesc();
}
