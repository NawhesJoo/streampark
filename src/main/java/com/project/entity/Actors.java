package com.project.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ACTORS")
@SequenceGenerator(name = "SEQ_ACTORS_NO", sequenceName = "SEQ_ACTORS_NO", initialValue = 1, allocationSize = 1)
public class Actors {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACTORS_NO")
    private BigInteger actors_no;

    private String actors_name;

    // //출연진
    // @ToString.Exclude
    // @OneToMany(mappedBy = "actors", cascade=CascadeType.REMOVE,
    // fetch=FetchType.LAZY)
    // private List<Casts> casts = new ArrayList<>();
}
