package com.example.alwayswin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: CORSConfig
 * @Description: 跨域资源访问
 * @Author: SQ
 * @Date: 2021-5-17
 */

@Configuration
public class CORSConfig implements WebMvcConfigurer{
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
                        .allowedOrigins("http://localhost:9527",   // 本地前端1
                                "http://192.168.86.25:9527",
                                "http://localhost:9528",   // 本地前端2
                                "http://192.168.86.25:9528",
                                "http://54.175.176.26:8081" // client
                        )
                        .allowCredentials(true);  //带上cookie信息
            }
        };
    }
}
