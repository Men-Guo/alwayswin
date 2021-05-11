package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.Order;
import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.service.impl.OrderServiceImpl;
import com.example.alwayswin.utils.RandomStringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    private static OrderServiceImpl orderService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        orderMapper = mock(OrderMapper.class);
        userMapper = mock(UserMapper.class);
        orderService = new OrderServiceImpl(orderMapper, userMapper);
    }

    //////////      getByOid        //////////////////////
    @Test
    public void happyPathWithGetOrderByOid() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number, 1, 1, "NYPD", 100,
                new Timestamp(System.currentTimeMillis()), "paid");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        Order order1 = orderService.getOrderByOid(1);
        assertNotNull(order1);
        assertEquals(number, order1.getNumber());
    }

    @Test
    public void InvalidOidExceptionWithGetOrderByOid() {
        when(orderMapper.getByOid(anyInt())).thenReturn(null);
        Order order1 = orderService.getOrderByOid(0);
        assertNull(order1);
    }

    @Test
    public void happyPathWithGetOrderByNumber() {
        String number = RandomStringUtil.createRandomString(8);

        Order order = new Order(1, number, 1, 1, "NYPD", 100,
                new Timestamp(System.currentTimeMillis()), "paid");
        when(orderMapper.getByNumber(anyString())).thenReturn(order);

        Order order1 = orderService.getOrderByNumber(number);
        assertNotNull(order1);
        assertEquals("NYPD", order1.getAddress());
    }

    @Test
    public void InvalidNumberExceptionWithGetOrderByNumber() {
        when(orderMapper.getByNumber(anyString())).thenReturn(null);
        Order order1 = orderService.getOrderByNumber(" ");
        assertNull(order1);
    }


    //////////      getByUid        /////////////////
    @Test
    public void happyPathWithGetOrderByUid() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        orderList.add(order);

        when(orderMapper.getByUid(anyInt())).thenReturn(orderList);

        List<Order> orderList1 = orderService.getOrdersByUid(1);
        assertNotNull(orderList1);
        assertEquals(1, orderList1.size());
    }

    @Test
    public void InvalidUidExceptionWithGetOrderByUid() {
        when(orderMapper.getByUid(anyInt())).thenReturn(null);
        List<Order> orderList1 = orderService.getOrdersByUid(0);

        assertNull(orderList1);
    }

    //////////      addOrder        /////////////////
    @Test
    public void happyPathWithAddOrder() {
        when(orderMapper.add(any(Order.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("uid", "1");
        param.put("name", "xiaoming");
        param.put("location", "NYPD");
        assertEquals(1, orderService.addOrder(param));
    }

    //////////      updateOrder        /////////////////
    @Test
    public void invalidStatusExceptionWithUpdateOrder() {
        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", "abcdefgh");
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD");
        param.put("payment", "1000");
        param.put("status", "hahaha");

        assertEquals(-1, orderService.updateOrder(1, 1, param));
    }

    ///    buyer     ////

    @Test
    public void happyPathWithBuyerPayOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "placed");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        when(orderMapper.update(any(Order.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "paid");

        UserInfo userInfo = new UserInfo();
        userInfo.setUid(1);
        userInfo.setBalance(2000);
        when(userMapper.getUserInfoByUid(anyInt())).thenReturn(userInfo);
        when(userMapper.updateUserInfo(any(UserInfo.class))).thenReturn(1);

        assertEquals(1, orderService.updateOrder(1, 1, param));
        assertEquals(1000, userInfo.getBalance());
    }

    @Test
    public void NoEnoughBalanceExceptionWithBuyerPayOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "placed");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        when(orderMapper.update(any(Order.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "paid");

        UserInfo userInfo = new UserInfo();
        userInfo.setUid(1);
        userInfo.setBalance(500);
        when(userMapper.getUserInfoByUid(anyInt())).thenReturn(userInfo);

        assertEquals(-4, orderService.updateOrder(1, 1, param));
    }

    @Test
    public void invalidStatusExceptionWithBuyerPayOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "placed");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        when(orderMapper.update(any(Order.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "received");

        UserInfo userInfo = new UserInfo();
        userInfo.setUid(1);
        userInfo.setBalance(5000);
        when(userMapper.getUserInfoByUid(anyInt())).thenReturn(userInfo);

        assertEquals(-2, orderService.updateOrder(1, 1, param));
    }

    @Test
    public void happyPathWithBuyerReceivedOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "shipped");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        when(orderMapper.update(any(Order.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "received");

        assertEquals(1, orderService.updateOrder(1, 1, param));
    }

    @Test
    public void invalidStatusExceptionWithBuyerReceivedOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "shipped");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        when(orderMapper.update(any(Order.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "placed");

        assertEquals(-2, orderService.updateOrder(1, 1, param));
    }

    @Test
    public void NoPermissionExceptionWithBuyerUpdateOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "paid");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "received");

        assertEquals(-3, orderService.updateOrder(1, 1, param));

    }

    ///    seller     ////
    @Test
    public void happyPathWithSellerSendOrder() {
        String number = RandomStringUtil.createRandomString(8);
        // buyer is 1
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "paid");

        Product product = new Product();
        product.setUid(2);  // seller is 2
        product.setPid(1);
        order.setProduct(product);

        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        when(orderMapper.update(any(Order.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "shipped");

        assertEquals(1, orderService.updateOrder(1, 2, param));
    }

    @Test
    public void invalidStatusExceptionWithSellerSendOrder() {
        String number = RandomStringUtil.createRandomString(8);
        // buyer is 1
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "paid");

        Product product = new Product();
        product.setPid(1);
        product.setUid(2);  // seller is 2
        order.setProduct(product);

        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "received");

        assertEquals(-2, orderService.updateOrder(1, 2, param));
    }

    @Test
    public void NoPermissionExceptionWithSellerUpdateOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "placed");

        Product product = new Product();
        product.setUid(2);  // seller is 2
        product.setPid(1);
        order.setProduct(product);

        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "shipped");

        assertEquals(-3, orderService.updateOrder(1, 2, param));
    }


    @Test
    public void NoPermissionExceptionWithOtherUserUpdateOrder() {
        String number = RandomStringUtil.createRandomString(8);
        Order order = new Order(1, number,
                1, 1, "", 1000,
                new Timestamp(System.currentTimeMillis()), "placed");

        Product product = new Product();
        product.setUid(2);  // seller is 2
        product.setPid(1);
        order.setProduct(product);

        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        Map<String, String> param = new HashedMap();
        param.put("oid", "1");
        param.put("number", number);
        param.put("uid", "1");
        param.put("pid", "1");
        param.put("address", "NYPD's next door");
        param.put("payment", "1000");
        param.put("status", "shipped");

        assertEquals(-3, orderService.updateOrder(1, 3, param));  // 3 既不是买家也不是卖家
    }



    //////////      deleteOrder        /////////////////
    @Test
    public void happyPathWithDeleteOrder() {
        Order order = new Order(1, "abcdefgh", 1, 1, "NYPD", 100,
                new Timestamp(System.currentTimeMillis()), "received");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);
        when(orderMapper.delete(anyInt())).thenReturn(1);
        assertEquals(1, orderService.deleteOrder(1));
    }

    @Test
    public void InvalidOidExceptionWithDeleteOrder() {
        when(orderMapper.getByOid(anyInt())).thenReturn(null);
        assertEquals(0, orderService.deleteOrder(0));
    }

    @Test
    public void InvalidStateExceptionWithDeleteOrder() {
        Order order = new Order(1, "abcdefgh", 1, 1, "NYPD", 100,
                new Timestamp(System.currentTimeMillis()), "paid");
        when(orderMapper.getByOid(anyInt())).thenReturn(order);
        assertEquals(-1, orderService.deleteOrder(1));
    }
    
}