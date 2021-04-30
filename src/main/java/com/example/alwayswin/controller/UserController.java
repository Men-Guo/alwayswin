package com.example.alwayswin.controller;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.UserService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import io.jsonwebtoken.Claims;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: UserController
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/register")
    public CommonResult register(@Validated @RequestBody Map params) {
        int res = userService.register(params);
        if (res == -1) {
            return CommonResult.validateFailure("Duplicate username, plz get a new one");
        }
        else if (res == -2) {
            return CommonResult.validateFailure("Password doesn't apply to the rule");
        }
        else if (res == -3) {
            return CommonResult.validateFailure("Passwords don't match");
        }
        else if (res == 0)
            return CommonResult.failure();
        return CommonResult.success(res);
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public CommonResult login(@Validated @RequestBody Map params) {
        String token = userService.login(params);
        if (token == null) {
            return CommonResult.validateFailure("Wrong username or password");
        }
        return CommonResult.success(token);
    }

    @ResponseBody
    @PutMapping("/logout")
    CommonResult logout(@RequestHeader("Authorization") String authHeader) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            if (userService.logout(uid) == 1)
                return CommonResult.success(null);
            else
                return CommonResult.failure();
        }
    }

    @ResponseBody
    @PutMapping("/user/changePassword")
    CommonResult changePassword(@RequestHeader("Authorization") String authHeader,
                                @RequestBody Map param) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            int res = userService.changePassword(uid, param);
            if (res == -1)
                return CommonResult.validateFailure("Old password doesn't match");
            else if (res == -2)
                return CommonResult.validateFailure("New Password should NOT be equal to old password");
            else if (res == -3)
                return CommonResult.validateFailure("Password should apply to the rule");
            else if (res == -4)
                return CommonResult.validateFailure("2 new passwords should match");
            else if (res == 0)
                return CommonResult.failure();
            else
                return CommonResult.success(res);
        }
    }

    @ResponseBody
    @GetMapping("/user/myInfo")
    CommonResult<UserInfo> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null)
                return CommonResult.failure();
            return CommonResult.success(userInfo);
        }
    }

    @ResponseBody
    @PutMapping("/user/myInfo/update")
    CommonResult updateUserInfo(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody Map param){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            int res = userService.updateUserInfo(uid, param);
            if (res == 1)
                return CommonResult.success(res);
            else return CommonResult.failure();
        }
    }

}
