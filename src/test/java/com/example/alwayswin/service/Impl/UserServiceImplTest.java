package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.entity.UserPreview;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.impl.UserServiceImpl;
import com.example.alwayswin.service.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceImplTest {
    private static UserService userService;
    @Mock
    private UserMapper userMapper;
    private ProductMapper productMapper;
    private BiddingMapper biddingMapper;
    private OrderMapper orderMapper;

    @BeforeEach
    public void init() {
        userMapper = mock(UserMapper.class);
        productMapper = mock(ProductMapper.class);
        biddingMapper = mock(BiddingMapper.class);
        orderMapper = mock(OrderMapper.class);
        userService = new UserServiceImpl(userMapper, productMapper, biddingMapper, orderMapper);

        ((UserServiceImpl) userService).passwordEncoder = new BCryptPasswordEncoder();
    }

    //////////////      login()     //////////////
    @Test
    public void happyPathWithLogin() {
        User user = new User(1, "Arthur", "$2a$10$wPsHefYMnGWXMkEKoOplfeabV074mpCMWe9OSM01.eyBHzMI5a1CW",
                "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUsername(any())).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("password", "ABC123");

        UserPreview userPreview = userService.login(param);
        assertNotNull(userPreview);

        Claims claims = JwtUtils.getClaimFromToken(userPreview.getToken());
        assertNotNull(claims);

        String username = claims.getSubject();
        assertEquals("Arthur", username);

        verify(userMapper).getByUsername("Arthur");

        userService.logout(1);
    }
    @Test
    public void wrongUsernameExceptionWithLogin() {
        User user = new User(1, "Arthur", "ABC123", "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUsername("arthur")).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("password", "abc123");  // wrong password

        UserPreview preview = userService.login(param);
        assertNull(preview);
    }

    @Test
    public void wrongPasswordExceptionWithLogin() {
        User user = new User(1, "Arthur", "ABC123", "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUsername("Arthur")).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("password", "abc123");  // wrong password

        UserPreview preview = userService.login(param);
        assertNull(preview);
    }

    @Test
    public void noSuchUserExceptionWithLogin() {
        Map<String, String> param = new HashMap<>();
        param.put("username", "BBBBBB");  // user doesn't exist
        param.put("password", "ABC123");

        UserPreview preview = userService.login(param);
        assertNull(preview);
    }


    //////////////      register     //////////////
    @Test
    public void happyPathWithRegister() {
        when(userMapper.getByUsername(anyString()))
                .thenReturn(null)
                .thenReturn(new User(12, "Ja3on_Jone5", "ABC123", "user", false, new Timestamp(System.currentTimeMillis())));

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "ABC123");
        param.put("password2", "ABC123");

        when(userMapper.add(any(User.class))).thenReturn(1);

        assertEquals(1,userService.register(param));
    }

    @Test
    public void DuplicateUsernameExceptionWithRegister() {
        User user = new User(0, "Ja3on_Jone5", "ABC123", "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUsername(anyString())).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "ABC123");
        param.put("password2", "ABC123");

        assertEquals(-1,userService.register(param));
    }

    @Test
    public void illegalUsernameWithTooShortLengthExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Jas");
        param.put("password1", "ABC12");
        param.put("password2", "ABC12");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalUsernameWithTooLongLengthExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "JasJasJasJasJasJasJasJasJasJasJasJasJasJasJasJasJasJasJasJasJas");
        param.put("password1", "ABC12");
        param.put("password2", "ABC12");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalUsernameWithNoLettersExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "12345678");
        param.put("password1", "123456");
        param.put("password2", "123456");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalUsernameWithIllegalCharsExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Jason--");
        param.put("password1", "ABC123");
        param.put("password2", "ABC123");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalPasswordWithTooShortLengthExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "ABC12");
        param.put("password2", "ABC12");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalPasswordWithTooLongLengthExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "ABC000000000000000000000000000000000000000000000000000000000000000000");
        param.put("password2", "ABC000000000000000000000000000000000000000000000000000000000000000000");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalPasswordWithNoLettersExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "123_456");
        param.put("password2", "123_456");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalPasswordWithNoDigitsExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "ABC_DEF");
        param.put("password2", "ABC_DEF");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void illegalPasswordWithIllegalCharsExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "ABC123--");
        param.put("password2", "ABC123--");

        assertEquals(-2,userService.register(param));
    }

    @Test
    public void passwordsNotEqualExceptionWithRegister() {
        when(userMapper.getByUsername(anyString())).thenReturn(null);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Ja3on_Jone5");
        param.put("password1", "ABC123");
        param.put("password2", "ABC124");

        assertEquals(-3,userService.register(param));
    }


    ///////////     logout      //////////////
    @Test
    public void happyPathWithLogout() {
        when(userMapper.updateLogoutStatus(anyInt(), eq(false))).thenReturn(1);
        assertEquals(1, userService.logout(1));
    }

    ///////////     changePassword      //////////////
    @Test
    public void happyPathWithChangePassword() {
        User user = new User(1, "Arthur",
                "$2a$10$wPsHefYMnGWXMkEKoOplfeabV074mpCMWe9OSM01.eyBHzMI5a1CW",
                "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUid(anyInt())).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("oldPassword", "ABC123");
        param.put("newPassword1", "ABC124");
        param.put("newPassword2", "ABC124");

        when(userMapper.updatePassword(anyInt(), any())).thenReturn(1);
        assertEquals(1, userService.changePassword(1, param));
    }

    @Test
    public void wrongOldPasswordExceptiomWithChangePassword() {
        User user = new User(1, "Arthur",
                "$2a$10$wPsHefYMnGWXMkEKoOplfeabV074mpCMWe9OSM01.eyBHzMI5a1CW",
                "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUid(anyInt())).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("oldPassword", "ABC122");  // real old f is ABC123
        param.put("newPassword1", "ABC124");
        param.put("newPassword2", "ABC124");

        when(userMapper.updatePassword(anyInt(), any())).thenReturn(1);
        assertEquals(-1, userService.changePassword(1, param));
    }
    @Test
    public void sameOldAndNewPasswordExceptionWithChangePassword() {
        User user = new User(1, "Arthur",
                "$2a$10$wPsHefYMnGWXMkEKoOplfeabV074mpCMWe9OSM01.eyBHzMI5a1CW",
                "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUid(anyInt())).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("oldPassword", "ABC123");
        param.put("newPassword1", "ABC123");
        param.put("newPassword2", "ABC123");

        when(userMapper.updatePassword(anyInt(), any())).thenReturn(1);
        assertEquals(-2, userService.changePassword(1, param));
    }
    @Test
    public void illegalNewPasswordExceptionWithChangePassword() {
        User user = new User(1, "Arthur",
                "$2a$10$wPsHefYMnGWXMkEKoOplfeabV074mpCMWe9OSM01.eyBHzMI5a1CW",
                "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUid(anyInt())).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("oldPassword", "ABC123");
        param.put("newPassword1", "ABC!!!");  // illegal password
        param.put("newPassword2", "ABC!!!");

        when(userMapper.updatePassword(anyInt(), any())).thenReturn(1);
        assertEquals(-3, userService.changePassword(1, param));
    }
    @Test
    public void newPasswordsNotEqualExceptionWithChangePassword() {
        User user = new User(1, "Arthur",
                "$2a$10$wPsHefYMnGWXMkEKoOplfeabV074mpCMWe9OSM01.eyBHzMI5a1CW",
                "user", false, new Timestamp(System.currentTimeMillis()));
        when(userMapper.getByUid(anyInt())).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("oldPassword", "ABC123");
        param.put("newPassword1", "ABC124");
        param.put("newPassword2", "ABC125");

        when(userMapper.updatePassword(anyInt(), any())).thenReturn(1);
        assertEquals(-4, userService.changePassword(1, param));
    }

    /////////       getUserInfo     ///////////
    @Test
    public void happyPathWithGetUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(1);
        userInfo.setGender("male");

        when(userMapper.getUserInfoByUid(1)).thenReturn(userInfo);
        assertEquals("male", userService.getUserInfo(1).getGender());
    }

    @Test
    public void happyPathWithUpdateUserInfo() {
        when(userMapper.updateUserInfo(any(UserInfo.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("gender", "male");
        param.put("email", "hahaha@gmail.com");

        assertEquals(1, userService.updateUserInfo(1, param));
    }

    @Test
    public void happyPathWithAddBalanceInUserInfo() {
        when(userMapper.updateUserBalance(any(UserInfo.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("balance", "100");
        param.put("amount", "1000");

        assertEquals(1, userService.updateUserBalance(1, param));
    }

    @Test
    public void happyPathWithReduceBalanceInUserInfo() {
        when(userMapper.updateUserBalance(any(UserInfo.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("balance", "1000");
        param.put("amount", "-1000");
        assertEquals(1, userService.updateUserBalance(1, param));
    }

    @Test
    public void noBalanceParamsWithUpdateBalance() {
        when(userMapper.updateUserBalance(any(UserInfo.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("amount", "-1000");
        assertEquals(-1, userService.updateUserBalance(1, param));
    }

    @Test
    public void noAmountParamsWithUpdateBalance() {
        when(userMapper.updateUserBalance(any(UserInfo.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("balance", "1000");
        assertEquals(-1, userService.updateUserBalance(1, param));
    }
}
