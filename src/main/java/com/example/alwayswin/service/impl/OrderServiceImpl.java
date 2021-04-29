package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Order;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.service.OrderService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderServiceImpl
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-29
 */
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Order getOrderByOid(int oid) {
        return orderMapper.getByOid(oid);
    }

    public Order getOrderByNumber(String number) {
        return orderMapper.getByNumber(number);
    }

    public List<Order> getOrdersByUid(int uid) {
        return orderMapper.getByUid(uid);
    }

    public int addOrder(Map param) {
        Order order = new Order();
        try {
            BeanUtils.populate(order, param);
        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return orderMapper.add(order);
    }

    public int updateOrder(int oid, Map param) {
        Order order = new Order();
        try {
            BeanUtils.populate(order, param);
            order.setOid(oid);
        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return orderMapper.update(order);
    }

    public int deleteOrder(int oid) {
        return orderMapper.delete(oid);
    }
}
