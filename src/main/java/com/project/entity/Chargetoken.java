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

@Data
@Entity
@Table(name = "CHARGETOKEN")
public class Chargetoken {
    
    @Id
    private String token;

    private BigInteger price;

    private BigInteger quantity;

    //결제내역
    @ToString.Exclude
    @OneToOne(mappedBy = "chargetoken", cascade = CascadeType.ALL , fetch =FetchType.LAZY)
    private Paychk paychk;
}
