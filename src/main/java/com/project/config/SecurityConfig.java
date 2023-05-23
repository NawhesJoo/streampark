package com.project.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.handler.CustomerLogoutSucessHandler;
import com.project.handler.LoginSuccessHandler;
import com.project.service.JeongService.SecurityMemberServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration   // 환경설정파일. 서버가 구동되기전에 호출됨.
@EnableWebSecurity // 시큐리티를 사용
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    
    final SecurityMemberServiceImpl  memberTableService; // member테이블과 연동되는 서비스
    // final SecurityServiceImpl1 student2TableService; //student2테이블과 연동되는 서비스
    // 관리자 테이블과 연동하는 서비스 ...   
       
    @Bean   // 객체를 생성함. (자동으로 호출됨.)
    @Order(value = 2)  //마지막 숫자로 변경
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain1");

        // 권한 설정 -> 권한 없으면 못들어오는 듯?
        http.authorizeRequests()
            .antMatchers("/customer/join.do").permitAll()
            .antMatchers("/seller/join.do").permitAll()
            .antMatchers("/admin/join.do").permitAll()
            .antMatchers("/admin", "/admin/*").hasAuthority("ROLE_ADMIN")  // 주소가 9090/ROOT/admin  ~~ 모든것
            .antMatchers("/seller", "/seller/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_SELLER")
            .antMatchers("/customer", "/customer/*").hasAnyAuthority("ROLE_CUSTOMER")
            .anyRequest().permitAll();

        // 403페이지 설정(접근권한 불가 시 표시할 화면)    
        http.exceptionHandling().accessDeniedPage("/403page.do");

        // 로그인 처리
        http.formLogin()
            .loginPage("/login.do")  //
            .loginProcessingUrl("/loginaction.do") //action은 
            .usernameParameter("id") 
            .passwordParameter("password")
            .successHandler(new LoginSuccessHandler())
            //.defaultSuccessUrl("/home.do") // 로그인 성공시 이동할 페이지
            .permitAll();

        // 로그아웃 처리(GET은 안됨 반드시 POST로 호출해야 됨.)
        http.logout()
            .logoutUrl("/logout.do")
            //.logoutSuccessUrl("/home.do")
            .logoutSuccessHandler(new CustomerLogoutSucessHandler())
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll();

        //post는 csrf를 전송해야하지만, 주소가 /api로 시작하는 모든url은  csrf가 없어도 됨
        http.csrf().ignoringAntMatchers("/api/**");

        //서비스 등록( 자동등록됨. 생략가능  )
        http.userDetailsService(memberTableService); 
        return http.build();
    }


    // 정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정, resources/static은 시큐리티 적용받지 않음.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 회원가입에서 사용했던 암호화 알고리즘 설정, 로그인에서도 같은 것을 사용해야 하니까
    @Bean  // 서버구동시 자동으로 실행됨 => @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
