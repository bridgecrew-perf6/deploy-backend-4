package com.vtw.deploy.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {//@CrossOrigin 전역 설정
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")
//				.allowedOrigins( "http://localhost:4200" )//한개이상의 CrossOrigin 설정 가능함.
//				.allowedOrigins("http://10.47.39.93:4200")
//				.allowedOrigins("http://10.47.39.76:4200")
//				.allowedOrigins("http://10.47.39.96:4200")
				.allowedOrigins("*")
				.allowedMethods("*");//모든 메서드 허용함
	}
}