package com.example.alwayswin.controller;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.entity.UserPreview;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.UserService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import io.jsonwebtoken.Claims;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RestController
public class UserController {

    @Resource
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/register")
    public CommonResult<Object> register(@RequestBody Map params) {
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
    public CommonResult<Object> login(@Validated @RequestBody Map params) {
        UserPreview userPreview = userService.login(params);
        if (userPreview == null) {
            return CommonResult.validateFailure("Wrong username or password");
        }
        return CommonResult.success(userPreview);
    }

    @ResponseBody
    @PostMapping(value = "/user/logout")
    public CommonResult<Object> logout(@RequestHeader("Authorization") String authHeader) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
            if (userService.logout(uid) == 1)
                return CommonResult.success(null);
            else
                return CommonResult.failure();
        }
    }

    @ResponseBody
    @PutMapping("/user/change-password")
    public CommonResult<Object> changePassword(@RequestHeader("Authorization") String authHeader,
                                @RequestBody Map param) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
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
            int uid = Integer.parseInt(claims.getAudience());
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null)
                return CommonResult.failure();
            return CommonResult.success(userInfo);
        }
    }

    @ResponseBody
    @GetMapping("/seller/{uid}")
    public CommonResult<UserPreview> getSellerInfo(@PathVariable("uid") Integer uid) {
        UserPreview userPreview = userService.getSellerInfo(uid);
        if (userPreview == null)
                return CommonResult.failure();
        return CommonResult.success(userPreview);
    }

    @ResponseBody
    @PutMapping("/user/my-info/update")
    public CommonResult<Object> updateUserInfo(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody Map param){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
            int res = userService.updateUserInfo(uid, param);  // ?????????????????????????????????, param????????????amount??????????????????
            if (res == 1)
                return CommonResult.success(res);
            else return CommonResult.failure();
        }
    }

    // todo: ????????????????????????????????????????????????
    @ResponseBody
    @PutMapping("/user/add-funds")
    public CommonResult<Object> updateBalance(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody Map param){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
            int res = userService.updateUserBalance(uid, param);  // {balance, amount}
            if (res == 1)
                return CommonResult.success(res);
            else if (res == -1)
                return CommonResult.failure("Parameters balance and amount needed!");
            else return CommonResult.failure();
        }
    }
}
