package com.project.service.JeongService;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.entity.Paychk;
import com.project.entity.Profile;
import com.project.repository.JeongRepositories.Projections.MemberProjection;

@Service
@Component
public interface JeongService {

    int insertPaychk(Paychk obj);
    
    Profile findProfileById(long profileno);

    MemberProjection findMemberById(String id);

    Paychk findPaychkTopByRegdate();


}
