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
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Profileimg")
@SequenceGenerator(name = "SEQ_PROFILEIMG_NO", sequenceName = "SEQ_PROFILEIMG_NO", initialValue = 1, allocationSize = 1)
public class Profileimg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILEIMG_NO")
    @Column(name = "NO")
    private BigInteger no;

    private String filename;

    @Lob
    @ToString.Exclude
    private byte[] filedata;

    private String filetype;

    private BigInteger filesize;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    private Date regdate;

    // private BigInteger profileno;

    //프로필
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFILENO", referencedColumnName = "PROFILENO")
    private Profile profile;
}
