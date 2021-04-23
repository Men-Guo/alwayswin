package com.example.alwayswin.service;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {

    // return token
    String login(String username, String password);

    int register(String username, String password);

    int logout(Integer uid);

    int changePassword(Integer uid, String newPassword);

    UserInfo getUserInfo(Integer uid);

    int updateUserInfo(int uid, Map param);
}
