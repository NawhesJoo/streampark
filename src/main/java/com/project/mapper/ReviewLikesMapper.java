package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.Reviewlikes;

@Mapper
public interface ReviewLikesMapper {

    public int insertProfile(Reviewlikes obj);

    public List<Reviewlikes> selectReviewlikesNo(Reviewlikes obj);

    public List<Reviewlikes> selectChklikes(Long reviewlikes_No);

    public int decreaseChklikes(Long reviewlikes_No);

    public int increaseChklikes(Long reviewlikes_No);



}
