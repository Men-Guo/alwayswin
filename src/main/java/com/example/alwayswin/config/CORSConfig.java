package com.example.alwayswin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: CORSConfig
 * @Description: 跨域资源访问
 * @Author: SQ
 * @Date: 2021-5-17
 */

public class CORSConfig {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                logger.info("--------------------------------------Cors all methods--------------------------------------");
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:9527",   // 本地前端
                                "http://54.175.176.26:8081",  // client
                                "http://localhost:8080"   // 本地后端
                        )
                        .allowCredentials(true);
            }
        };
    }
}