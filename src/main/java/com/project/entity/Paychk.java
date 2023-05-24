package com.project.entity;

import java.math.BigInteger;
import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "PAYCHK")
@SequenceGenerator(name = "SEQ_PAYCHK_NO", sequenceName = "SEQ_PAYCHK_NO", initialValue = 1, allocationSize = 1)
public class Paychk {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYCHK_NO")
    private BigInteger paychk_no;

    private String type;
  
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    private BigInteger price;
    //멤버
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member;

    // 요금제
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade", referencedColumnName = "grade")
    private Fee fee;

    //토큰 충전
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token", referencedColumnName = "token")
    private Chargetoken chargetoken;
        

}
