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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "Videolist")
@SequenceGenerator(name = "SEQ_VIDEO_NO", sequenceName = "SEQ_VIDEO_NO", initialValue = 1, allocationSize = 1)
public class Videolist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIDEO_NO")
    @Column(name = "VIDEOCODE")
    private BigInteger videocode;

    private String title;

    private String keyword;

    private String pd;

    private String chkage;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    private String opendate;

    private BigInteger price;

    private BigInteger episode;

    @Lob
    private String linkurl;

    //임시변수
    @Transient
    private Long imgno;
    // 연결

    //출연진
    @OneToMany(mappedBy = "casts_to_videocode", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Casts> castList = new ArrayList<>();

    //시청목록
    @OneToMany(mappedBy = "videolist", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Watchlist> watchlists = new ArrayList<>();
    
    //작품이미지
    @OneToMany(mappedBy = "videolist", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Videoimg> videoimgs = new ArrayList<>();

    //구매내역
    @OneToMany(mappedBy = "videolist", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Paymentlist> paymentlists = new ArrayList<>();

    //나중에 볼영상
    @OneToMany(mappedBy = "videolist", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Interestlist> interestlists = new ArrayList<>();
}
