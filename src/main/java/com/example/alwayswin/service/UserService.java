package com.example.alwayswin.service;

import com.example.alwayswin.entity.ResponseMsg;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    ResponseMsg login(String username, String password);

    ResponseMsg register(String username, String password);

    ResponseMsg logout(Integer uid);

    ResponseMsg changePassword(Integer uid, String newPassword);

    ResponseMsg getUserInfo(Integer uid);

    ResponseMsg updateUserInfo(int uid, Map param);
}
