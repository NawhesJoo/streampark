package com.project.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "Review")
@SequenceGenerator(name = "SEQ_REVIEW_NO", sequenceName = "SEQ_REVIEW_NO", initialValue = 1, allocationSize = 1)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW_NO")
    @Column(name = "REVIEW_NO")
    private BigInteger review_no;

    @Lob
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    private Date regdate;

    private BigInteger reportcnt;

    // private BigInteger viewno;

    private BigInteger likes;

    private BigInteger profileno;

    // 리뷰 좋아요
    @OneToMany(mappedBy = "Review_to_reviewlikes", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Reviewlikes> board = new ArrayList<>();

    // 시청목록
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewno", referencedColumnName = "viewno")
    private Watchlist watchlist;
    
}
