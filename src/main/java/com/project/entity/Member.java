package com.project.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "MEMBER")
public class Member {
    
    @Id
    private String id;

    private String pw;

    private String name;

    private String phone; 

    private String birth;

    private BigInteger token;

    private String gender;

    private String role;

    private String email;

    private BigInteger membershipchk;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;


    
    // 결제 내역
    @ToString.Exclude
    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Paychk> paychk = new ArrayList<>();

    // 프로필
    @ToString.Exclude
    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Profile> profile = new ArrayList<>();


    

}
