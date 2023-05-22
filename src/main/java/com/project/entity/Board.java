package com.project.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "BOARD")
@SequenceGenerator(name = "SEQ_BOARD_NO", sequenceName = "SEQ_BOARD_NO", initialValue = 1, allocationSize = 1)
public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD_NO")
    private BigInteger no;

    private String title;

    @Lob
    private String content;
    
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;
    
    
    // 프로필 
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "profileno", referencedColumnName = "profileno")
    private Profile profile;

    // 문의 답글     
    @OneToMany(mappedBy = "board", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Qnareply> qnareply = new ArrayList<>();
   
}
