package com.project.entity;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

//멤버십 요금제
@Data
@Entity
@Table(name = "FEE")
public class Fee {

    @Id
    private BigInteger grade;

    private BigInteger price;

    private String name;

    private BigInteger capacity;

    private String expiration;

    //결제 내역
    @ToString.Exclude
    @OneToOne(mappedBy = "fee", cascade = CascadeType.ALL , fetch =FetchType.LAZY)
    private Paychk paychk;


}
