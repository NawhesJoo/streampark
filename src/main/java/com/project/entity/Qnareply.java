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
@Table(name = "Qnareply")
@SequenceGenerator(name = "SEQ_QNAREPLY_NO", sequenceName = "SEQ_QNAREPLY_NO", initialValue = 1, allocationSize = 1)
public class Qnareply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QNAREPLY_NO")
    @Column(name = "REPLYNO")
    private BigInteger REPLYNO;

    @Lob
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    private Date regdate;

    // 문의
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no", referencedColumnName = "no")
    private Board board;

    //프로필
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileno", referencedColumnName = "profileno")
    private Profile profile;

}
