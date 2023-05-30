package com.project.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, BigInteger> {
    
    // 리뷰 전체목록
    List<Review> findAllByOrderByRegdateDesc();

    // 해당 비디오코드에 맞는 리뷰 목록
    @Query(value = " SELECT wl.viewno, wl.viewdate, wl.videocode, wl.profileno, r.review_no, r.content, r.regdate, r.likes, r.reportcnt FROM WATCHLIST wl, REVIEW r WHERE wl.VIDEOCODE=:videocode AND wl.viewno = r.viewno ORDER BY wl.viewdate DESC ", nativeQuery=true)
    List<Review> findByVideolist_VideocodeIgnoreCaseContainingOrderByViewdateDesc(BigInteger videocode);

    List<Review> findAllByOrderByLikesDesc();

    @Query(value = " SELECT wl.viewno, wl.viewdate, wl.videocode, wl.profileno, r.review_no, r.content, r.regdate, r.likes, r.reportcnt FROM WATCHLIST wl, REVIEW r WHERE wl.VIDEOCODE=:videocode AND wl.viewno = r.viewno ORDER BY r.likes DESC ", nativeQuery=true)
    List<Review> findByVideolist_VideocodeIgnoreCaseContainingOrderByLikesDesc(BigInteger videocode);

    // List<Review> findByReviewlikes_chklikesOrderByReviewnoDesc();
}
