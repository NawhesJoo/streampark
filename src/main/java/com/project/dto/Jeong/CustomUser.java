package com.project.dto.Jeong;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString(exclude = {"password"})
public class CustomUser extends User {
	private String id;    //username
	private String password; //password
	private Collection<GrantedAuthority> authorities; //role
	private String name;
	

	// User의 생성자 기본구조
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	// 기본구조에서 추가할 내용포함(이름, 나이)
	public CustomUser(String username, String password, Collection<GrantedAuthority> authorities, String name) {
		super(username, password, authorities);
		this.authorities = authorities;
		this.id = username;
		this.password = password;
		this.name = name;
	}

}