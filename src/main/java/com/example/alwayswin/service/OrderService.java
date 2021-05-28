package com.example.alwayswin.service;

import com.example.alwayswin.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    Order getOrderByOid(int oid);

    Order getOrderByNumber(String number);

    List<Order> getOrdersByUid(int uid);

    int addOrder(Map param);

    int updateOrder(int oid, int uid, Map param);  // buyer 和 seller都可以update order，此处uid用来指定操作者是谁

    int deleteOrder(int oid);

    List<Order> getSellerOrderByUid(int uid);
}
