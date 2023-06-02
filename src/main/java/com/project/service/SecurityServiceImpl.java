package com.project.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entity.Member;
import com.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityServiceImpl implements UserDetailsService {
    final String format = "SecurityServiceImpl => {}";
    final MemberRepository mRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(format, username);
        // 아이디를 전달해서 정보를 받아옴 암호까지 받아옴
        Member member = mRepository.findById(username).get();
        if(member != null) { //가져올 정보가 있었다. 존재하는 아이디가 있음
            // User를 이용할 경우(세션내용 => 아이디, 암호, 권한)
            return User.builder().username(member.getId()).password(member.getPw()).roles(member.getRole()).build();
            
            // CustomUser을 사용할 경우(세션내용 => 아이디, 암호, 권한, 이름, 나이)
        //     String[] strRole = { "ROLE_" + member.getRole() };
        //     Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);
        //     return new CustomUser(member.getId(), member.getPassword(), role, member.getName(), member.getAge());
        }

        // 아이디가 없는 경우는 User로 처리.
        return User.builder().username("_").password("_").roles("_").build();
    }
}