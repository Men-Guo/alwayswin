package com.example.alwayswin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.alwayswin.mapper")
public class AlwayswinApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlwayswinApplication.class, args);
    }

}
