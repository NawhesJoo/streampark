package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.Reviewreport;

@Mapper
public interface ReviewReportMapper {

    public int insertProfile2(Reviewreport obj);

    public List<Reviewreport> selectReviewreportNo(Reviewreport obj);

    public List<Reviewreport> selectChkreport(Long reviewreport_No);

    public int increaseChkreport(Long reviewreport_No);
    
}
