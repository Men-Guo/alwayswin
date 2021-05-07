package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.Order;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// todo: do order test after bidding is finished
class OrderServiceImplTest {

    private static OrderServiceImpl orderService;
    @Mock
    private OrderMapper orderMapper;

    @BeforeEach
    public void init() {
        orderMapper = mock(OrderMapper.class);
        orderService = new OrderServiceImpl(orderMapper);
    }

    //////////      getByOid        //////////////////////
    @Test
    public void happyPathWithGetOrderByOid() {
        // Integer oid,String number, Integer uid, Integer pid, String address,
        //                double payment, Timestamp createTime, String status
        Order order = new Order();
        when(orderMapper.getByOid(anyInt())).thenReturn(order);

        Order order1 = orderService.getOrderByOid(1);
        assertNotNull(order1);
        assertEquals("", order1.getNumber());
    }

    @Test
    public void InvalidOidExceptionWithGetOrderByOid() {
        when(orderMapper.getByOid(anyInt())).thenReturn(null);
        Order order1 = orderService.getOrderByOid(0);
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
        assertEquals(2, orderList1.size());
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
        when(orderMapper.add(any())).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("uid", "1");
        param.put("name", "xiaoming");
        param.put("location", "NYPD");
        assertEquals(1, orderService.addOrder(param));
    }

    //////////      updateOrder        /////////////////
    public void happyPathWithUpdateOrder() {
        when(orderMapper.update(any(Order.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("name", "xiaoming");
        param.put("location", "NYPD");
        orderService.updateOrder(1, param);
    }


    //////////      deleteOrder        /////////////////
    @Test
    public void happyPathWithDeleteOrder() {
        when(orderMapper.delete(anyInt())).thenReturn(1);
        assertEquals(1, orderService.deleteOrder(1));
    }

    @Test
    public void InvalidOidExceptionWithDeleteOrder() {
        when(orderMapper.delete(anyInt())).thenReturn(0);
        assertEquals(0, orderService.deleteOrder(0));
    }
    
}