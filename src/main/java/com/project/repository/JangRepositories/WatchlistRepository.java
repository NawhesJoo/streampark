package com.project.repository.JangRepositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Watchlist;


@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, BigInteger> {

    public List<Watchlist> findAllByOrderByViewdateDesc();

    List<Watchlist> findByVideolist_titleIgnoreCaseContainingOrderByViewdateDesc(String title, Pageable pageable);

    long countByVideolist_titleContaining(String title);

    List<Watchlist> findByVideolist_pdIgnoreCaseContainingOrderByViewdateDesc(String pd, Pageable pageable);

    long countByVideolist_pdContaining(String pd);

    List<Watchlist> findByVideolist_chkageIgnoreCaseContainingOrderByViewdateDesc(String chkage, Pageable pageable);

    long countByVideolist_chkageContaining(String chkage);

    // 프로필번호로 시청목록 날짜 기준 내림차순 정렬
    List<Watchlist> findByProfile_profilenoOrderByViewdateDesc(BigInteger profileno);
}
