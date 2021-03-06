package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.UserPreview;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.security.UserDetailsServiceImpl;
import com.example.alwayswin.service.UserService;
import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public PasswordEncoder passwordEncoder;

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

//    public String login(Map param) {
//        User user = new User();
//        try {
//            UserDetailsService userDetailsService = new UserDetailsServiceImpl();
//            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
//            // matches(CharSequence rawPassword, String encodedPassword)
//            if(!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())){
//                logger.warn("Wrong password");
//                System.out.println("Wrong password");
//            }
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception e) {
//            logger.debug(e.getMessage(), e);
//            e.printStackTrace();
//        }
//    userMapper.updateLoginStatus(user.getUid(), true, new Timestamp(System.currentTimeMillis()));
//        return JwtUtils.generateToken(userMapper.getByUsername(user.getUsername()));
//    }

    public UserPreview getSellerInfo(int uid){
        return userMapper.getPreviewByUid(uid);
    }

    // for testf
    public UserPreview login(Map param) {
        String username = (String)param.get("username");
        String password = (String)param.get("password");
        try {
            User user = userMapper.getByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                userMapper.updateLoginStatus(user.getUid(), true, new Timestamp(System.currentTimeMillis()));
                UserInfo userInfo = userMapper.getUserInfoByUid(user.getUid());
                return new UserPreview(user.getUid(), user.getUsername(), user.getRole(),
                        userInfo.getPortrait(), JwtUtils.generateToken(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        String password1 = (String)param.get("password1");
        String password2 = (String)param.get("password2");

        // ???????????????
        if (userMapper.getByUsername(username) != null)
            return -1;
        // ???????????????????????????
        if (!isValidUsername(username) || !isValidPassword(password1)) {
            logger.warn("Username and Password should apply to the rule");
            return -2;
        }
        // ???????????????
        if (!password2.equals(password1)) {
            logger.warn("Passwords don't match");
            return -3;
        }

        User user = new User();
        user.setUsername(username);
        // ??????????????????
        user.setPassword(passwordEncoder.encode(password1));
        // ??????user table
        // trigger??????????????????????????????user info
        return userMapper.add(user);
    }


    public int logout(Integer uid) {
        return userMapper.updateLogoutStatus(uid, false);
    }

    public int changePassword(Integer uid, Map param) {
        String oldPassword = (String)param.get("oldPassword");
        String newPassword1 = (String)param.get("newPassword1");
        String newPassword2 = (String)param.get("newPassword2");

        //??????????????????
        if(!passwordEncoder.matches(oldPassword, userMapper.getByUid(uid).getPassword())) {
            logger.warn("Old password doesn't match");
            return -1;
        }
        // ??????????????????
        else if (newPassword1.equals(oldPassword)) {
            logger.warn("New Password should not be equal to old password");
            return -2;
        }
        //??????????????????
        else if (!isValidPassword(newPassword1)) {
            logger.warn("Password should apply to the rule");
            return -3;
        }
        // ????????????????????????
        else if (!newPassword1.equals(newPassword2)) {
            logger.warn("2 new passwords should match");
            return -4;
        }

       return userMapper.updatePassword(uid, passwordEncoder.encode(newPassword1));
    }

    /*
     * @Description: ????????????????????????6~20????????????(required)?????????(optional)??????, ??????????????????????????????
     * @Param: [username]
     * @Return: boolean
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    private boolean isValidUsername(String username) {
        if (username.length() < 6 || username.length() > 20 )
            return false;
        else {
            int digitCnt = 0, letterCnt = 0, underlineCnt = 0;
            for (char c : username.toCharArray()) {
                if (Character.isDigit(c))
                    digitCnt++;
                else if (Character.isLetter(c))
                    letterCnt++;
                else if (c == '_') {
                    underlineCnt++;
                }
                else
                    return false;
            }
            return  digitCnt >= 0 && letterCnt > 0 && underlineCnt >= 0;
        }
    }


    /*
     * @Description: ?????????????????????6~20 ?????????????????????, ??????????????????????????????
     * @Param: [password]
     * @Return: boolean
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    private boolean isValidPassword(String password) {
        if (password.length() < 6 || password.length() > 20 )
            return false;
        else {
            int digitCnt = 0, letterCnt = 0, underlineCnt = 0;
            for (char c : password.toCharArray()) {
                if (Character.isDigit(c))
                    digitCnt++;
                else if (Character.isLetter(c))
                    letterCnt++;
                else if (c == '_') {
                    underlineCnt++;
                }
                else
                    return false;
            }
            return  digitCnt > 0 && letterCnt > 0 && underlineCnt >= 0;
        }
    }


        ////////////////        User Info           ////////////////////

    public UserInfo getUserInfo(Integer uid) {
        return userMapper.getUserInfoByUid(uid);
    }

    public int updateUserInfo(int uid, Map param) {
        UserInfo userInfo = new UserInfo();
        try {
            BeanUtils.populate(userInfo, param);
            userInfo.setUid(uid);
        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return userMapper.updateUserInfo(userInfo);
    }

    // ??????????????????
    public int updateUserBalance(int uid, Map param) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(uid);

        int amount = 0, balance = 0;
        if (param.containsKey("balance")) {
            balance = Integer.parseInt((String) param.get("balance"));
        }
        else
            return -1;
        if (param.containsKey("amount")) {
            amount = Integer.parseInt((String) param.get("amount"));  // amount ????????????
            userInfo.setBalance(balance + amount);
        }
        else
            return -1;
        return userMapper.updateUserBalance(userInfo);
    }

}
