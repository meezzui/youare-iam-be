package com.letter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Custom-Header","Authorization") // 나가는 헤더 설정
                .allowedHeaders("Access-Control-Request-Headers") // 클라이언트에서 헤더에서 접근 가능하게 하기 위한 설정
                .allowCredentials(true)
                .maxAge(3600);
    }
}
