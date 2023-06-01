package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration // 환경설정파일. 서버가 구동되기전에 호출됨.
@EnableWebSecurity // 시큐리티를 사용
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    // final SecurityServiceImpl  memberTableService;
    @Bean // 객체를 생성함. (자동으로 호출됨.)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain1");

        http.authorizeRequests()
                .antMatchers("/member/*").permitAll()
                .antMatchers("/member","/member/info.do").hasAnyAuthority("ROLE_C","ROLE_A")
                .antMatchers("/kdh/*").hasAnyAuthority("ROLE_C","ROLE_A")
                .antMatchers("/profile/*").hasAnyAuthority("ROLE_C","ROLE_A")
                .antMatchers("/kdh/videoupdate.do").hasAuthority("ROLE_A")
                .antMatchers("/kdh/manageactor.do").hasAuthority("ROLE_A")
                .antMatchers("/kdh/videodelete.do").hasAuthority("ROLE_A")
                .antMatchers("/pay/*").hasAnyAuthority("ROLE_C","ROLE_A")
                .antMatchers("/qna/*").hasAnyAuthority("ROLE_C","ROLE_A")
                .antMatchers("/watchlist/*").hasAnyAuthority("ROLE_C","ROLE_A")
                .antMatchers("/view/*").hasAnyAuthority("ROLE_C","ROLE_A")
                .anyRequest().permitAll();

        // 로그인 처리
        http.formLogin()
                .loginPage("/member/login.do")
                .loginProcessingUrl("/member/loginaction.do")
                .usernameParameter("id")
                .passwordParameter("pw")
                .defaultSuccessUrl("/profile/profilelist.do")
                .permitAll();

        // 로그이웃 설정
        http.logout()
                .logoutUrl("/logout.do")
                .logoutSuccessUrl("/member/main.do")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();
                
        // 권한 설정
        // http.authorizeRequests()
        // .antMatchers("/customer/join.do").permitAll()

        // .anyRequest().permitAll();

        // 403페이지 설정(접근권한 불가 시 표시할 화면)
        // http.exceptionHandling().accessDeniedPage("/403page.do");

        // post는 csrf를 전송해야하지만, 주소가 /api로 시작하는 모든url은 csrf가 없어도 됨
        http.csrf().ignoringAntMatchers("/streampark/**");
        http.csrf().ignoringAntMatchers("/api/**");

        // http.userDetailsService(memberTableService); 

        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        return http.build();

    }

    // 정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정, resources/static은 시큐리티 적용받지 않음.
    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    // return (web) ->
    // web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    // }

    // 회원가입에서 사용했던 암호화 알고리즘 설정, 로그인에서도 같은 것을 사용해야 하니까
    @Bean // 서버구동시 자동으로 실행됨 => @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
