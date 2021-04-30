package com.example.alwayswin.security;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserDetailsServiceImpl
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-20
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // Spring Security 角色名称默认使用 "ROLE_" 开头
        // authorities.add 可以增加多个用户角色，对于一个用户有多种角色的系统来说，
        // 可以通过增加用户角色表、用户--角色映射表，存储多个用户角色信息
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        // 给 Spring Security 传入用户名、用户密码、用户角色。
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
