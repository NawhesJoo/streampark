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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Videoimg")
@SequenceGenerator(name = "SEQ_VIDEOIMG_NO", sequenceName = "SEQ_VIDEOIMG_NO", initialValue = 1, allocationSize = 1)
public class Videoimg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIDEOIMG_NO")
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

    // private BigInteger videocode;

    // 작품 목록
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videocode", referencedColumnName = "videocode")
    private Videolist videolist;

}
