package com.project.service.JSH;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dto.Profiledto;
import com.project.entity.Profile;
import com.project.mapper.JSH.ProfileMapper;
import com.project.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    
    final ProfileRepository pRepository;
    final ProfileMapper pMapper;
    
    @Override
    public int createProfile(String keyword, String id, String nickname) {
        return pMapper.createProfile(keyword, id, nickname);
    }

    @Override
    public List<Profile> selectprofile(String id) {
       return pRepository.findByMember_id(id);
    }
    
    @Override
    public Profile findByNickname(String nickname) {
        return pRepository.findByNickname(nickname);
    }

    @Override
    public long countByNickname(String nickname) {
        return pRepository.countByNickname(nickname);
    }

    @Override
    public int updateNickname(String nickname, String newNickname, String profilepw) {
        return pMapper.updateNickname(nickname, newNickname, profilepw);
    }

    @Override
    public Profiledto loginProfile(String nickname, String profilepw) {
        return pMapper.loginProfile(nickname, profilepw);
    }

    @Override
    public int updateNickname1(String nickname, String newNickname) {
        return pMapper.updateNickname1(nickname, newNickname);
    }

    @Override
    public int deleteProfile(String nickname, String profilepw) {
        return pMapper.deleteProfile(nickname, profilepw);
    }

    @Override
    public int deleteProfileNoPw(String nickname) {
        return pMapper.deleteProfileNoPw(nickname);
    }

}
