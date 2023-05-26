package com.project.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                log.info("LoginSuccessHandler => {}", authentication.toString());
                String role =authentication.getAuthorities().toArray()[0].toString();
                log.info("LoginSuccessHandler", role);

                HttpSession httpSession = request.getSession();
                String backurl = (String)httpSession.getAttribute("url");

                if(role.equals("ROLE_CUSTOMER")){
                    response.sendRedirect(request.getContextPath()+"/customer/home.do");
                }
               else if(role.equals("ROLE_SELLER")){
                    response.sendRedirect(request.getContextPath()+"/seller/home.do");
                }
                else if(role.equals("ROLE_ADMIN")){
                    response.sendRedirect(request.getContextPath()+"/admin/home.do");
                }
                else{
                    response.sendRedirect( request.getContextPath()+"/home.do");
                }
    }
    
}
