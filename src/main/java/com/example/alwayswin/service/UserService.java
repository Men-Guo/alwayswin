package com.example.alwayswin.service;

import com.example.alwayswin.utils.DateUtil;
import com.example.alwayswin.entity.ResponseMsg;
import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
public class UserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private BiddingMapper biddingMapper;

    @Resource
    private OrderMapper orderMapper;

    public UserService(UserMapper userMapper, ProductMapper productMapper,
                       BiddingMapper biddingMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.biddingMapper = biddingMapper;
        this.orderMapper = orderMapper;
    }

    /*
     * @Description: user login
     * @Param: [username, password]
     * @Return: com.example.alwayswin.entity.ResponseMsg
     * @Author: SQ
     * @Date: 2021-4-20
     **/

    public ResponseMsg login(String username, String password) {
        ResponseMsg msg = new ResponseMsg();
        try {
            // 验证用户名和密码
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Wrong username or password");
            return msg;
        }

        msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Login successful");

        User user = userMapper.getByUsername(username);
        // set user login status and login time
        userMapper.updateLoginStatus(user.getUid(), true, new Timestamp(System.currentTimeMillis()));

        // put user and token into response
        msg.getResponseMap().put("uid", user.getUid());   // uid放在msg 中就可以到处使用辽
        msg.getResponseMap().put("username", user.getUsername());
        msg.getResponseMap().put("token", JwtUtils.generateToken(user));
        return msg;
    }

    /*
     * @Description: user registration
     * @Param: [username, password]
     * @Return: com.example.alwayswin.entity.ResponseMsg
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    public ResponseMsg register(String username, String password) {
        ResponseMsg msg = new ResponseMsg();
        try {
            User user = new User();
            user.setUsername(username);
            // 密码加密存储
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            // 加入user
            userMapper.add(user);

            user = userMapper.getByUsername(username);
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(user.getUid());
            userInfo.setRegisDate(new Date(System.currentTimeMillis()));
            // 加入userInfo
            userMapper.addUserInfo(userInfo);

        } catch (DataAccessException e) {
            // username is unique
            msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Duplicate username");
            return msg;
        }
        msg.setStatusAndMessage(HttpServletResponse.SC_OK, "New user created");

        // 将用户名和密码存入msg
        msg.getResponseMap().put( "username", username);
        msg.getResponseMap().put("password", password);
        return msg;
    }

    public ResponseMsg logout(Integer uid) {
        ResponseMsg msg = new ResponseMsg();
        try{
            if (userMapper.updateLogoutStatus(uid, false) > 0)
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "logout successful");
            else
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "logout failed");
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg changePassword(Integer uid, String newPassword) {
        ResponseMsg msg = new ResponseMsg();
        try{
            if (userMapper.updatePassword(uid, newPassword) > 0)
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Changing password successful");
            else
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Changing password failed");

        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


        ////////////////        User Info           ////////////////////

    public ResponseMsg getUserInfo(Integer uid) {
        ResponseMsg msg = new ResponseMsg(HttpServletResponse.SC_BAD_REQUEST, "No userInfo");
        try {
            UserInfo userInfo = userMapper.getUserInfoByUid(uid);
            if (userInfo != null) {
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "UserInfo found");
                msg.getResponseMap().put("userInfo", userInfo);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg updateUserInfo(int uid, Map param) {
        ResponseMsg msg = new ResponseMsg();
        try{
            UserInfo userInfo = userMapper.getUserInfoByUid(uid);

            if(param.containsKey("portrait"))
                userInfo.setPortrait(param.get("portrait").toString());
            if (param.containsKey("phone"))
                userInfo.setPhone(param.get("phone").toString());
            if(param.containsKey("email"))
                userInfo.setEmail(param.get("email").toString());
            if (param.containsKey("gender"))
                userInfo.setGender(param.get("gender").toString());
            if(param.containsKey("balance"))
                userInfo.setBalance(Double.parseDouble(param.get("balance").toString()));
            if(param.containsKey("birthday")){
                Date birthday = DateUtil.String2Date(param.get("birthday").toString(), "yyyy-MM-dd");
                userInfo.setBirthday(birthday);
            }

            if(userMapper.updateUserInfo(userInfo) > 0){
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Update userInfo successfully");
            }else{
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Update userInfo failed");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
