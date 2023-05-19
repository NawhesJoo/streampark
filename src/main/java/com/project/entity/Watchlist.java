package com.project.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "WATCHLIST")
@SequenceGenerator(name = "SEQ_WATCHLIST_NO", sequenceName = "SEQ_WATCHLIST_NO", initialValue = 1, allocationSize = 1)
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WATCHLIST_NO")
    @Column(name = "VIEWNO")
    private BigInteger viewno;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp // 변경시에도 날짜 정보 변경
    private Date viewdate;

    // private BigInteger videocode;

    // private BigInteger profileno;

    // 프로필
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileno", referencedColumnName = "profileno")
    private Profile profile;

    // 작품 목록
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videocode", referencedColumnName = "videocode")
    private Videolist videolist;

    // 리뷰
    @ToString.Exclude
    @OneToOne(mappedBy = "watchlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;
}
