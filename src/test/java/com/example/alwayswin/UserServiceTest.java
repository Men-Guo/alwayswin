/*
package com.example.alwayswin;

import com.example.alwayswin.entity.ResponseMsg;
import com.example.alwayswin.entity.User;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
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
        
        userService = new UserService(userMapper, productMapper, biddingMapper, orderMapper);
    }

    //////////////login()//////////////
    @Test
    public void happy_path_with_login() {
        User user = new User();
        user.setUsername("Ben");
        user.setPassword("ABC123");

        when(userMapper.getByUsername("Ben")).thenReturn(user);

        ResponseMsg msg = userService.login("Ben", "ABC123");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());

        assertEquals("Ben", msg.getResponseMap().get("username").toString());
        assertEquals("2", msg.getResponseMap().get("uid").toString());
        verify(userMapper).getByUsername("Ben");
    }
//
//    @Test
//    public void exception_when_login() throws Exception {
//        when(userMapper.getByName("Alias")).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = userService.login("Alias", "123456");
//        assertEquals(404, msg.getStatus());
//        verify(userMapper).getByName("Alias");
//    }

//    @Test
//    public void no_such_user_when_login() throws Exception {
//        List<User> users = new ArrayList<>();
//        when(userMapper.getByName("Alias")).thenReturn(users);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = userService.login("Alias", "123456");
//        assertEquals(204, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        verify(userMapper).getByName("Alias");
//    }
//
//    @Test
//    public void wrong_password_when_login() throws Exception {
//        User user = new User(1, "Alias", null, null, null, null, "123456", null, null, null);
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        when(userMapper.getByName("Alias")).thenReturn(users);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = userService.login("Alias", "654321");
//        assertEquals(206, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        User e = (User) msg.getResponseMap().get("user");
//        assertNull(e);
//        verify(userMapper).getByName("Alias");
//    }

}
*/
