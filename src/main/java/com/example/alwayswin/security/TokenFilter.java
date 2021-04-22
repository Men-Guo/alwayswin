
package com.example.alwayswin.security;

import com.example.alwayswin.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TokenFilter
 * @Description: Token 拦截器，拦截所有请求并验证 Token 是否有效，有效则授权通过，无效则由 Spring Security 根据配置拦截无效请求
 * @Author: SQ
 * @Date: 2021-4-20
 */


@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class TokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException{

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String authorizationValue;
        try {
            // 存储 Token 的 Headers Key与 Value，默认是 Authorization
            authorizationValue = httpRequest.getHeader("Authorization");
        } catch (Exception e) {
            authorizationValue = null;
        }

        // Token 开头部分 默认 Bearer 开头, 注意空格
        final String bearer = "Bearer ";
        if (authorizationValue != null && authorizationValue.startsWith(bearer)) {
            // 抽取token
            String token = authorizationValue.substring(bearer.length());
            // 使用token 判断用户身份
            User user = JwtUtils.validateToken(token);
            if (user != null) {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
                // 传入用户名、用户密码、用户角色。 这里的密码随便写的，用不上
                UserDetails userDetails = new
                        org.springframework.security.core.userdetails.User(user.getUsername(), "", authorities);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(userDetails.getUsername());
                // 授权
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
