package com.example.alwayswin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: CORSConfig
 * @Description: 跨域资源访问
 */

// 正确的解决跨域问题的方法时使用CorsFilter过滤器
@Configuration
public class CORSConfig {

    private CorsConfiguration corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:9527");
        corsConfiguration.addAllowedOrigin("http://localhost:9528");
        corsConfiguration.addAllowedOrigin( "http://54.175.176.26:8081");
        corsConfiguration.addAllowedOrigin("http://192.168.1.191:9527");
        corsConfiguration.addAllowedOrigin("http://192.168.1.191:9528");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }
}

/*
 * 使用此方法配置之后再使用自定义拦截器时跨域相关配置就会失效。
原因是请求经过的先后顺序问题，当请求到来时会先进入拦截器中，而不是进入Mapping映射中，所以返回的头信息中并没有配置的跨域信息。浏览器就会报跨域异常。
 **/

//@Configuration
//public class CORSConfig implements WebMvcConfigurer {
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        logger.info("-------------------------Cors all methods----------------------------");
//        //本应用的所有方法都会去处理跨域请求
//        registry.addMapping("/**")
//                //允许远端访问的域名
//                .allowedOrigins("http://localhost:9527",   // 本地前端1
//                        "http://192.168.86.25:9527",
//                        "http://localhost:9528",   // 本地前端2
//                        "http://192.168.86.25:9528",
//                        "http://54.175.176.26:8081" // client
//                )
//                //允许请求的方法("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .allowedMethods("*")
//                //允许请求头
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//}
