package com.ssafy.campcino.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // FE 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true) // 쿠키 허용
                .allowedHeaders("*");
    }
}
