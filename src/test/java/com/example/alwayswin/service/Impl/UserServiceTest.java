package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    private static UserServiceImpl userService;
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

    //////////////login()//////////////
    @Test
    public void happy_path_with_login() {

    }

}
