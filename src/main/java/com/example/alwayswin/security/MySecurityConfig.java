/*
package com.example.alwayswin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.Filter;

*/
/**
 * @ClassName: MySecurityConfig
 * @Description: Spring Security 配置，配置密码存储加密算法，添加拦截器，关闭 Session 管理器，允许跨域访问, 允许登录和注册的 API 无授权访问
 * @Author: SQ
 * @Date: 2021-4-20
 *//*


@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private Filter tokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt 密码
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // 配置 CSRF 关闭,允许跨域访问
        httpSecurity.csrf().disable();

        // 关闭 Session
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 允许 登录 注册的 api 的无授权访问，其他需要授权访问
        httpSecurity.authorizeRequests()
                .antMatchers("/user/login", "/user/register")
                .permitAll().anyRequest().authenticated();

        // 添加拦截器
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }
}*/
