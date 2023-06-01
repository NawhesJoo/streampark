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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

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

    @ColumnDefault("0")
    private BigInteger reportcnt= BigInteger.valueOf(0);

    // private BigInteger viewno;
    @ColumnDefault("0")
    private BigInteger likes=BigInteger.valueOf(0);

    private BigInteger profileno;

    // 리뷰 좋아요
    @OneToMany(mappedBy = "review", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Reviewlikes> reviewlikes = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Reviewreport> reviewreport = new ArrayList<>();

    // 시청목록
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewno", referencedColumnName = "viewno")
    private Watchlist watchlist;
    
}
