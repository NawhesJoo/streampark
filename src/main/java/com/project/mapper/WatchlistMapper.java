package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.VideowatchlistView;

@Mapper
public interface WatchlistMapper {

    List<VideowatchlistView> watchListSelectViewdate(long profileno);

    List<VideowatchlistView> watchListSearchTitle(String keyword);

    List<VideowatchlistView> watchListSearchPd(String keyword);

    List<VideowatchlistView> watchListSearchChkage(String keyword);

    List<VideowatchlistView> watchListVideolistAll();
}
