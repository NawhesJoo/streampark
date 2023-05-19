package com.project.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "INTERESTLIST")
@SequenceGenerator(name = "SEQ_INTEREST_NO", sequenceName = "SEQ_INTEREST_NO", initialValue = 1, allocationSize = 1)
public class Interestlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INTEREST_NO")
    private BigInteger interestno;

    
    // private BigInteger videocode;
    
    // private BigInteger profileno;

    //작품 테이블 연결
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "videocode", referencedColumnName = "videocode")
    private Videolist videolist;

    //프로필 테이블 연결
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "profileno", referencedColumnName = "profileno")
    private Profile profile;
}
