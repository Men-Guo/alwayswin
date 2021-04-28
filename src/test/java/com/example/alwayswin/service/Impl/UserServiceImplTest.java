package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.User;
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
    }

    //////////////      login()     //////////////
    @Test
    public void happyPathWithLogin() {
        User user = new User(1, "Arthur", "ABC123", "user", false, new Timestamp(0));
        when(userMapper.getByUsername("Arthur")).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("password", "ABC123");

        String token = userService.login(param);
        assertNotNull(token);

        Claims claims = JwtUtils.getClaimFromToken(token);
        assertNotNull(claims);

        String username = claims.getSubject();
        assertEquals("Arthur", username);

        verify(userMapper).getByUsername("Arthur");

        userService.logout(1);
    }

    @Test
    public void wrongPasswordExceptionWithLogin() {
        User user = new User(1, "Arthur", "ABC123", "user", false, new Timestamp(0));
        when(userMapper.getByUsername("Arthur")).thenReturn(user);

        Map<String, String> param = new HashMap<>();
        param.put("username", "Arthur");
        param.put("password", "abc123");  // wrong password

        String token = userService.login(param);
        assertNull(token);
    }

    @Test
    public void noSuchUserExceptionWithLogin() {
        Map<String, String> param = new HashMap<>();
        param.put("username", "BBBBBB");  // user doesn't exist
        param.put("password", "ABC123");

        String token = userService.login(param);
        assertNull(token);
    }


    //////////////      register     //////////////
    @Test
    public void happyPathWithRegister() {
        Map<String, String> param = new HashMap<>();
        param.put("username", "Jason");
        param.put("password", "ABC123");
        param.put("password1", "ABC123");

        userService.register(param);
    }

    @Test
    public void DuplicateUsernameExceptionWithRegister() {

    }

    @Test
    public void illegalPasswordExceptionWithRegister() {

    }

    @Test
    public void passwordsNotEqualExceptionWithRegister() {

    }
}
