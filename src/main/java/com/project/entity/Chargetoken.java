package com.project.entity;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @OneToMany(mappedBy = "chargetoken", cascade = CascadeType.ALL , fetch =FetchType.LAZY)
    private List<Paychk> paychk;
}
