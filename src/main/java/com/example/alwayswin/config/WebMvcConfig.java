package com.example.alwayswin.config;

import com.example.alwayswin.service.FigureService;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: WebMvcConfig
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-22
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // todo
    public static String imageToStorage = "";
//    public static String imageToStorage = new ApplicationHome(FigureService.class).getSource().
//            getParentFile().getPath() + "/images/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                // dynamic directory
                .addResourceLocations("file:"+imageToStorage);
    }
}
