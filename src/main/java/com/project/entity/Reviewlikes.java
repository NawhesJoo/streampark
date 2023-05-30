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

import org.springframework.boot.context.properties.bind.DefaultValue;

import lombok.Data;
import lombok.ToString;
@Data
@Entity
@Table(name = "REVIEWLIKES")
@SequenceGenerator(name = "SEQ_REVIEWLIKES_NO", sequenceName = "SEQ_REVIEWLIKES_NO", initialValue = 1, allocationSize = 1)
public class Reviewlikes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEWLIKES_NO")
    private BigInteger reviewlikes_no;

    private BigInteger profileno;

    private BigInteger chklikes;   

    // private BigInteger review_no;
    
    //리뷰
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_no", referencedColumnName = "review_no")
    private Review Review_to_reviewlikes;

    

}