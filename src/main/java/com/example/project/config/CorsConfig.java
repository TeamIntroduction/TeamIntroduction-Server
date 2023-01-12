package com.example.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true)
//                .allowedHeaders("access-control-allow-origin", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//                        "Access-Control-Request-Headers", "content-type", "Authorization", "Access-Control-Allow-Origin")
//                .exposedHeaders(HttpHeaders.SET_COOKIE)
                .maxAge(3000);
    }
}