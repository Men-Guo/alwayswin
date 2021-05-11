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
    public CommonResult register(@RequestBody Map params) {
        int res = userService.register(params);
        if (res == -1) {
            return CommonResult.validateFailure("Duplicate username, plz get a new one");
        }
        else if (res == -2) {
            return CommonResult.validateFailure("Username or Password doesn't apply to the rule");
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
    @PostMapping(value = "/user/logout")
    public CommonResult logout(@RequestHeader("Authorization") String authHeader) {
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
    @PutMapping("/user/change-password")
    public CommonResult changePassword(@RequestHeader("Authorization") String authHeader,
                                @RequestBody Map param) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            int res = userService.changePassword(uid, param);
            if (res == -1)
                return CommonResult.failure("Old password doesn't match");
            else if (res == -2)
                return CommonResult.failure("New Password should NOT be equal to old password");
            else if (res == -3)
                return CommonResult.failure("Password should apply to the rule");
            else if (res == -4)
                return CommonResult.failure("2 new passwords should match");
            else if (res == 0)
                return CommonResult.failure();
            else
                return CommonResult.success(res);
        }
    }

    @ResponseBody
    @GetMapping("/user/my-info")
    public CommonResult<UserInfo> getUserInfo(@RequestHeader("Authorization") String authHeader) {
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
    @PutMapping("/user/my-info/update")
    public CommonResult updateUserInfo(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody Map param){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            int res = userService.updateUserInfo(uid, param);  // 如果涉及到充钱扣钱的话, param里放一个amount，带正负号的
            if (res == 1)
                return CommonResult.success(res);
            else return CommonResult.failure();
        }
    }

    // todo: 现在是个假接口，以后接入支付模块
    @ResponseBody
    @PutMapping("/user/add-funds")
    public CommonResult updateBalance(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody Map param){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            int res = userService.updateUserBalance(uid, param);  // {balance, amount}
            if (res == 1)
                return CommonResult.success(res);
            else if (res == -1)
                return CommonResult.failure("Parameters balance and amount needed!");
            else return CommonResult.failure();
        }
    }

}
