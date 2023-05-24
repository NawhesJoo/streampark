package com.project.streampark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@PropertySource(value = { "classpath:global.properties" }) // 직접만든 환경설정파일위치
@MapperScan(basePackages = { "com.project.mapper" })
@ComponentScan(basePackages = { "com.project.controller", "com.project.service", "com.project.config",
		"com.project.restcontroller", "com.project.controller.jpa","com.project.controller.mybatis","com.project.filter", "com.project.jangrestcontroller" }) // 컨트롤러, 서비스 위치, 시큐리티 환경설정
@EntityScan(basePackages = { "com.project.entity" }) // 엔티티 위치
@EnableJpaRepositories(basePackages = { "com.project.repository" }) // 저장소 위치
public class StreamparkApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamparkApplication.class, args);
	}

}
