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

    // // 해당 비디오코드에 맞는 리뷰 목록
    // @Query(value = " SELECT r.* FROM REVIEW r, VIDEOLIST vl WHERE vl.VIDEOCODE=:videocode ORDER BY videdate DESC ", nativeQuery=true)
    // List<Review> findByVideolist_VideocodeIgnoreCaseContainingOrderByViewdateDesc();

    List<Review> findAllByOrderByLikesDesc();

    // List<Review> findByReviewlikes_chklikesOrderByReviewnoDesc();
}
