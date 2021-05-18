package com.example.alwayswin.service;

import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.entity.UserPreview;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {

    // return token
    UserPreview login(Map param);

    int register(Map param);

    int logout(Integer uid);

    int changePassword(Integer uid, Map param);

    UserInfo getUserInfo(Integer uid);

    int updateUserInfo(int uid, Map param);

    int updateUserBalance(int uid, Map param);

    UserPreview getSellerInfo(int pid);
}
