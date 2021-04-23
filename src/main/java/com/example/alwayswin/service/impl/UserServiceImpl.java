package com.example.alwayswin.service.impl;

import com.example.alwayswin.service.UserService;
import com.example.alwayswin.utils.DateUtil;
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
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
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
    private AuthenticationManager authenticationManager;

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

    public String login(String username, String password) {
        try {
            // 验证用户名和密码
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            logger.info(e.getMessage(), e);
        }
        return JwtUtils.generateToken(userMapper.getByUsername(username));
    }

    /*
     * @Description: user registration
     * @Param: [username, password]
     * @Return: com.example.alwayswin.entity.int
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    public int register(String username, String password) {
        int res = 0;
        try {
            User user = new User();
            user.setUsername(username);
            // 密码加密存储
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            // 加入user
            res = userMapper.add(user);

            user = userMapper.getByUsername(username);
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(user.getUid());
            userInfo.setRegisDate(new Date(System.currentTimeMillis()));
            // 加入userInfo
            res = userMapper.addUserInfo(userInfo);

        } catch (DataAccessException e) {
            // username is unique
            logger.info(e.getMessage(), e);
        }
        return res;
    }

    public int logout(Integer uid) {
        return userMapper.updateLogoutStatus(uid, false);
    }

    public int changePassword(Integer uid, String newPassword) {
       return userMapper.updatePassword(uid, newPassword);
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
