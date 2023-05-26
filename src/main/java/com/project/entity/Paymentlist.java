package com.project.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Paymentlist")
@SequenceGenerator(name = "SEQ_PAYMENTLIST_NO", sequenceName = "SEQ_PAYMENTLIST_NO", initialValue = 1, allocationSize = 1)
public class Paymentlist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYMENTLIST_NO")
    @Column(name = "PAYMENTLISTNO")
    private BigInteger paymentlistno;

    // private BigInteger profileno;

    // private BigInteger videocode;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    private Date regdate;

    //작품
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "videocode", referencedColumnName = "videocode")
    private Videolist videolist;

    // 프로필
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "profileno", referencedColumnName = "profileno" )
    private Profile profile;    
    
}
