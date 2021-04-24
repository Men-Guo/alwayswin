package com.example.alwayswin.service.impl;

import com.example.alwayswin.security.UserDetailsServiceImpl;
import com.example.alwayswin.service.UserService;
import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Map;

/**
 * @ClassName: UserService
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */


@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private BiddingMapper biddingMapper;

    @Resource
    private OrderMapper orderMapper;

    public UserServiceImpl(UserMapper userMapper, ProductMapper productMapper,
                           BiddingMapper biddingMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.biddingMapper = biddingMapper;
        this.orderMapper = orderMapper;
    }

    /*
     * @Description: user login
     * @Param: [username, password]
     * @Return: String
     * @Author: SQ
     * @Date: 2021-4-20
     **/

    public String login(Map param) {
        User user = new User();
        BeanUtils.copyProperties(param, user);
        try {
            UserDetailsService userDetailsService = new UserDetailsServiceImpl();
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            // matches(CharSequence rawPassword, String encodedPassword)
            if(!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())){
                logger.warn("Wrong password");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            logger.warn("Wrong username or password", e);
        }
        return JwtUtils.generateToken(userMapper.getByUsername(user.getUsername()));
    }

    /*
     * @Description: user registration
     * @Param: [username, password]
     * @Return: com.example.alwayswin.entity.int
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    public int register(Map param) {
        String username = (String) param.get("username");
        String password = (String)param.get("password");
        String password2 = (String)param.get("password2");

        // 重复用户名
        if (userMapper.getByUsername(username) != null)
            return -1;

        // 密码不合法
        if (!isValidPassword(password)) {
            logger.warn("Password should apply to the rule");
            return -2;
        }
        // 密码不相等
        if (!password2.equals(password)) {
            logger.warn("Passwords don't match");
            return -3;
        }

        User user = new User();
        user.setUsername(username);
        // 密码加密存储
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        // 加入user table
        userMapper.add(user);

        user = userMapper.getByUsername(username);
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(user.getUid());
        userInfo.setRegisDate(new Date(System.currentTimeMillis()));
        // 加入userInfo
        return userMapper.addUserInfo(userInfo);
    }

    public int logout(Integer uid) {
        return userMapper.updateLogoutStatus(uid, false);
    }

    public int changePassword(Integer uid, Map param) {
        String oldPassword = (String)param.get("oldPassword");
        String newPassword1 = (String)param.get("newPassword1");
        String newPassword2 = (String)param.get("newPassword2");

        //旧密码不吻合
        if(!passwordEncoder.matches(oldPassword, userMapper.getByUid(uid).getPassword())) {
            logger.warn("Old password doesn't match");
            return -1;
        }
        // 新旧密码一致
        else if (newPassword1.equals(oldPassword)) {
            logger.warn("New Password should not be equal to old password");
            return -2;
        }
        //新密码不合法
        else if (isValidPassword(newPassword1)) {
            logger.warn("Password should apply to the rule");
            return -3;
        }
        // 两个新密码不吻合
        else if (!newPassword1.equals(newPassword2)) {
            logger.warn("2 new passwords should match");
            return -4;
        }
       return userMapper.updatePassword(uid, passwordEncoder.encode(newPassword1));
    }

    /*
     * @Description: 密码规则。长度大于等于6位且数字字母混合, 字符滚粗
     * @Param: [password]
     * @Return: boolean
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    private boolean isValidPassword(String password) {
        if (password.length() < 6)
            return false;
        else {
            int digitCnt = 0, letterCnt = 0, otherCnt = 0;
            for (char c : password.toCharArray()) {
                if (Character.isDigit(c))
                    digitCnt++;
                else if (Character.isLetter(c))
                    letterCnt++;
                else {
                    otherCnt++;
                    break;
                }
            }
            return digitCnt > 0 && letterCnt > 0 && otherCnt == 0;
        }
    }


        ////////////////        User Info           ////////////////////

    public UserInfo getUserInfo(Integer uid) {
        return userMapper.getUserInfoByUid(uid);
    }

    public int updateUserInfo(int uid, Map param) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(param, userInfo);
        userInfo.setUid(uid);

        return userMapper.updateUserInfo(userInfo);
    }
}
