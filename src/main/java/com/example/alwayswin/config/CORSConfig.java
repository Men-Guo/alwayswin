package com.example.alwayswin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: CORSConfig
 * @Description: 跨域资源访问
 */

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("-------------------------Cors all methods----------------------------");
        //本应用的所有方法都会去处理跨域请求
        registry.addMapping("/**")
                //允许远端访问的域名
                .allowedOrigins("http://localhost:9527",   // 本地前端1
                        "http://192.168.86.25:9527",
                        "http://localhost:9528",   // 本地前端2
                        "http://192.168.86.25:9528",
                        "http://54.175.176.26:8081" // client
                )
                //允许请求的方法("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedMethods("*")
                //允许请求头
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
