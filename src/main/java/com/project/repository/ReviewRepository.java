package com.project.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, BigInteger> {

    // 리뷰 전체목록(관리자용)
    List<Review> findAllByOrderByRegdateDesc();
    
    // 리뷰 전체목록(해당 사용자)
    @Query(value = " SELECT r.*, p.nickname FROM REVIEW r, PROFILE p WHERE r.profileno=:profileno AND p.profileno=:profileno ORDER BY regdate DESC ", nativeQuery = true)
    List<Review> findAllByOrderByRegdateDesc(BigInteger profileno);

    // 해당 비디오코드에 맞는 리뷰 목록
    @Query(value = " SELECT wl.viewno, wl.viewdate, wl.videocode, wl.profileno, r.review_no, r.content, r.regdate, r.likes, r.reportcnt FROM WATCHLIST wl, REVIEW r WHERE wl.VIDEOCODE=:videocode AND wl.viewno = r.viewno ORDER BY wl.viewdate DESC ", nativeQuery=true)
    List<Review> findByVideolist_VideocodeIgnoreCaseContainingOrderByViewdateDesc(BigInteger videocode);

    // likes 순서대로 정렬
    @Query(value = " SELECT r.*, p.nickname FROM REVIEW r, PROFILE p WHERE r.profileno=:profileno AND p.profileno=:profileno ORDER BY likes DESC ", nativeQuery = true)
    List<Review> findAllByOrderByLikesDesc(BigInteger profileno);

    @Query(value = " SELECT wl.viewno, wl.viewdate, wl.videocode, wl.profileno, r.review_no, r.content, r.regdate, r.likes, r.reportcnt FROM WATCHLIST wl, REVIEW r WHERE wl.VIDEOCODE=:videocode AND wl.viewno = r.viewno ORDER BY r.likes DESC ", nativeQuery=true)
    List<Review> findByVideolist_VideocodeIgnoreCaseContainingOrderByLikesDesc(BigInteger videocode);

    // @Query(value = " SELECT p.nickname FROM review r, profile p WHERE r.profileno=:profileno AND p.profileno=:profileno ", nativeQuery = true)
    // List<Review> findByProfile_Nickname(BigInteger profileno); // 프로필 번호로 프로필 닉네임 가져오기
    // List<Review> findByProfilenoOrderByRegdateDesc();

    // List<Review> findByReviewlikes_chklikesOrderByReviewnoDesc();
}
