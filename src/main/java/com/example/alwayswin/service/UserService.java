package com.example.alwayswin.service;

import com.example.alwayswin.entity.ResponseMsg;
import com.example.alwayswin.entity.User;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.security.JWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName: UserService
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

// todo: 修改密码，修改个人信息，logout
@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JWT jwt;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private BiddingMapper biddingMapper;

    @Resource
    private OrderMapper orderMapper;

    public UserService(JWT jwt, UserMapper userMapper, ProductMapper productMapper,
                       BiddingMapper biddingMapper, OrderMapper orderMapper) {
        this.jwt = jwt;
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

    public ResponseMsg login(String username, String password){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(HttpServletResponse.SC_NOT_FOUND, "Requested resource not found");
        try{
            User user = userMapper.getByUsername(username);
            if(user == null){
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Wrong username or password");
            }
            else {
                // encoded password matches password from db
                if(new BCryptPasswordEncoder().encode(password).equals(user.getPassword())){
                    msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Login successful");

                    // set user login status and login time
                    user.setStatus(true);
                    user.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
                    userMapper.update(user);

                    // put user and token into response
                    msg.getResponseMap().put("user", user);
                    String token = jwt.generateToken(Long.valueOf(user.getUid()));
                    msg.getResponseMap().put("token", token);
                }
                else{
                    msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Wrong username or password");
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /*
     * @Description: user regislation
     * @Param: [username, password]
     * @Return: com.example.alwayswin.entity.ResponseMsg 
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    public ResponseMsg register(String username, String password) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(HttpServletResponse.SC_NOT_FOUND, "Requested resource not found");
        try{
            if(userMapper.getByUsername(username) != null){
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Duplicate username");
            }
            else {
                if(isValidPassword(password)){
                    msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Registration successful");

                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(new BCryptPasswordEncoder().encode(password));

                    userMapper.add(user);
                }
                else{
                    msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Invalid password");
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
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

}
