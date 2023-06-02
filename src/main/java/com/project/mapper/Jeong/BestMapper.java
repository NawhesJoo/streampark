package com.project.mapper.Jeong;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.dto.Jeong.BestList;

@Mapper
public interface BestMapper {
    
    @Select({
        "SELECT w.VIDEOCODE , COUNT(*) cnt FROM WATCHLIST w  GROUP BY VIDEOCODE ORDER BY cnt DESC, VIDEOCODE ASC LIMIT (5);"
    })
    public List<BestList> selectTop5Watchlist();

    @Select({
        "SELECT VIDEOCODE , COUNT(*) cnt FROM WATCHLIST w , REVIEW r WHERE r.VIEWNO = w.VIEWNO GROUP BY VIDEOCODE ORDER BY VIDEOCODE  ASC, cnt DESC LIMIT(5)"
    })
    public List<BestList> selectTop5Review();
}
