package com.project.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Watchlist;


@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, BigInteger> {

    public List<Watchlist> findAllByOrderByViewdateDesc();

    @Query(value = " SELECT wl.profileno, wl.viewno, wl.viewdate, vl.videocode, vl.title, vl.keyword, vl.pd, vl.chkage, vl.opendate, vl.price, vl.episode FROM watchlist wl, videolist vl where title like '%' || :keyword || '%' AND wl.videocode=vl.videocode AND wl.profileno=:profileno order by wl.viewdate DESC ", nativeQuery=true)
    List<Watchlist> findByVideolist_titleIgnoreCaseContainingOrderByViewdateDesc(String keyword, BigInteger profileno);

    long countByVideolist_titleContaining(String title);

    @Query(value = " SELECT wl.profileno, wl.viewno, wl.viewdate, vl.videocode, vl.title, vl.keyword, vl.pd, vl.chkage, vl.opendate, vl.price, vl.episode FROM watchlist wl, videolist vl where pd like '%' || :keyword || '%' AND wl.videocode=vl.videocode AND wl.profileno=:profileno order by wl.viewdate DESC ", nativeQuery=true)
    List<Watchlist> findByVideolist_pdIgnoreCaseContainingOrderByViewdateDesc(String keyword, BigInteger profileno);

    long countByVideolist_pdContaining(String pd);

    @Query(value = " SELECT wl.profileno, wl.viewno, wl.viewdate, vl.videocode, vl.title, vl.keyword, vl.pd, vl.chkage, vl.opendate, vl.price, vl.episode FROM watchlist wl, videolist vl where chkage like '%' || :keyword || '%' AND wl.videocode=vl.videocode AND wl.profileno=:profileno order by wl.viewdate DESC ", nativeQuery=true)
    List<Watchlist> findByVideolist_chkageIgnoreCaseContainingOrderByViewdateDesc(String keyword, BigInteger profileno);

    long countByVideolist_chkageContaining(String chkage);


    // 프로필번호로 시청목록 날짜 기준 내림차순 정렬
    @Query(value = " SELECT wl.profileno, wl.viewno, wl.viewdate, vl.videocode, vl.title, vl.keyword, vl.pd, vl.chkage, vl.opendate, vl.price, vl.episode FROM watchlist wl, videolist vl where wl.videocode=vl.videocode AND profileno=:profileno order by wl.viewdate DESC ", nativeQuery=true)
    List<Watchlist> findByProfile_profilenoOrderByViewdateDesc(BigInteger profileno);


    // long countByProfile_profilenoContaining(BigInteger profileno);
}
