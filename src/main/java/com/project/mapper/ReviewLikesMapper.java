package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.Reviewlikes;

@Mapper
public interface ReviewLikesMapper {

    public int insertProfile(Reviewlikes obj);

    public List<Reviewlikes> selectChklikes(Long reviewNo);

    public int decreaseChklikes(Long reviewlikesNo);

    public List<Reviewlikes> selectReviewlikesNo();


}
