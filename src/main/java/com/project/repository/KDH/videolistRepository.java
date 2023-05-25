package com.project.repository.KDH;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Videolist;

@Repository
public interface videolistRepository extends JpaRepository<Videolist, BigInteger>{

    Videolist findByTitleAndEpisode(String title, BigInteger episode);
    
    int deleteByTitle(String title);

    List<Videolist> findByTitle(String title);

    BigInteger countByTitle(String title);

    List<Videolist> findByEpisodeOrderByOpendateDesc(BigInteger episode);

    List<Videolist> findByTitleOrderByEpisodeAsc(String title);
    
    
}
