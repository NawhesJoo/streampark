package com.project.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Profile")
@SequenceGenerator(name = "SEQ_PROFILE_NO", sequenceName = "SEQ_PROFILE_NO", initialValue = 1, allocationSize = 1)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE_NO")
    @Column(name = "PROFILENO")
    private Long profileno;

    private String nickname;

    // private String id;

    private String profilepw;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    private Long reviewban;

    private String keyword;

    // 멤버
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member;

    // ---- 위는 실제 컬럼

    //구매내역    
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paymentlist> paymentlist;

    // 시청목록
    @ToString.Exclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Watchlist> watchlist;

    // 프로필이미지
    @ToString.Exclude
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profileimg profileimg;
    

    //문의글
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board> board = new ArrayList<>();

    //문의답글
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Qnareply> qnareply = new ArrayList<>();

    //나중에 볼 영상
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Interestlist> interestlists = new ArrayList<>();

}
