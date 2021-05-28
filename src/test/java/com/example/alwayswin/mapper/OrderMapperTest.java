package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    ////////      getByOid      ///////
    @Test
    public void happyPathWithGetByOid() {
        Order order = orderMapper.getByOid(1);
        System.out.println(order.toString());
        assertNotNull(order);
        assertEquals(1, order.getUid());
        assertEquals("Play Station 1000", order.getProductPreview().getTitle());
    }

    @Test
    public void noSuchOidExceptionWithGetByOid() {
        Order order = orderMapper.getByOid(0);
        assertNull(order);
    }

    ////////      getByOid      ///////
    @Test
    public void happyPathWithGetByNumber() {
        Order order = orderMapper.getByNumber("20210412XYZ01");
        assertNotNull(order);
        assertEquals(1, order.getUid());
    }

    @Test
    public void noSuchNumberExceptionWithGetByOid() {
        Order order = orderMapper.getByNumber("20210412XYZ00");
        assertNull(order);
    }


    ////////      getByUid      ///////
    @Test
    public void happyPathWithGetByUid() {
        List<Order> orderList = orderMapper.getByUid(1);
        assertNotNull(orderList);
        assertEquals(2, orderList.size());
    }

    @Test
    public void noSuchUidExceptionWithGetByOid() {
        List<Order> orderList = orderMapper.getByUid(0);
        assertNotNull(orderList);
        assertEquals(0, orderList.size());
    }

    ////////      addOrder      ///////
    @Test
    public void happyPathWithAddOrder() {
        Order order = new Order(0, "abc", 2, 2, "",
                2000, new Timestamp(System.currentTimeMillis()), "placed");
        assertEquals(1, orderMapper.add(order));

        order = orderMapper.getByNumber("abc");
        assertNotNull(order);
        assertEquals("placed", order.getStatus());

        assertEquals(1, orderMapper.delete(order.getOid()));
    }

    ////////      updateOrder      ///////
    @Test
    public void happyPathWithUpdateOrder() {
        Order order = orderMapper.getByOid(1);
        assertNotNull(order);

        String oldStatus = order.getStatus();

        // update
        order.setStatus("received");
        assertEquals(1, orderMapper.update(order));

        // verify
        order = orderMapper.getByOid(1);
        assertEquals("received", order.getStatus());

        // change back
        order.setStatus(oldStatus);
        assertEquals(1, orderMapper.update(order));

        // verify
        order = orderMapper.getByOid(1);
        assertEquals(oldStatus, order.getStatus());
    }

    ////////      deleteOrder      ///////
    @Test
    public void happyPathWithDeleteOrder() {
        Order order = new Order(0, "abc", 2, 2, "",
                2000, new Timestamp(System.currentTimeMillis()), "placed");
        assertEquals(1, orderMapper.add(order));

        order = orderMapper.getByNumber("abc");
        assertNotNull(order);
        assertEquals("placed", order.getStatus());

        assertEquals(1, orderMapper.delete(order.getOid()));
    }

    @Test
    public void noOidExceptionWithDeleteByOid() {
        assertEquals(0, orderMapper.delete(0));
    }

    @Test
    public void testSellerOrder(){
        System.out.println(orderMapper.getSellerOrder(2));
    }
}