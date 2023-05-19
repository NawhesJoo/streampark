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

import lombok.Data;

@Data
@Entity
@Table(name = "CASTS")
@SequenceGenerator(name = "SEQ_CASTS_NO", sequenceName = "SEQ_CASTS_NO", initialValue = 645, allocationSize = 1)
public class Casts {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CASTS_NO")
    private BigInteger cast_no;


    /* @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "profileno", referencedColumnName = "profileno")
    private Profile board_to_profile; */
    
    //작품
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "videocode", referencedColumnName = "videocode")
    private Videolist casts_to_videocode;

    //배우
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "actors_no", referencedColumnName = "actors_no")
    private Actors actors;

}
