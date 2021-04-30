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

    int updateOrder(int oid, Map param);

    int deleteOrder(int oid, Map param);
}
