package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Address;
import com.example.alwayswin.entity.Order;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.service.OrderService;
import com.example.alwayswin.utils.RandomStringUtil;
import com.example.alwayswin.utils.enums.OrderStatusCode;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderServiceImpl
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-29
 */

@Service
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
            order.setNumber(RandomStringUtil.createRandomString(8));
            order.setCreateTime(new Timestamp(System.currentTimeMillis()));
            order.setStatus(OrderStatusCode.PLACED.getStatus());
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
        Order order = orderMapper.getByOid(oid);
        if (order == null)
            return 0;
        // 检查是否可以删除该订单
        if (OrderStatusCode.isDeletable(order.getStatus()))
            return orderMapper.delete(oid);
        else
            return -1;
    }
}
