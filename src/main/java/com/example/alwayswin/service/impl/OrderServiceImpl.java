package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Order;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.mapper.OrderMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.service.OrderService;
import com.example.alwayswin.utils.RandomStringUtil;
import com.example.alwayswin.utils.enumUtil.OrderStatusCode;
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

    @Resource
    UserMapper userMapper;

    public OrderServiceImpl(OrderMapper orderMapper, UserMapper userMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
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

    //  placed -> paid, buyer pay; paid -> shipped, seller; shipped -> received, buyer
    // buyer 和 seller都可以update order，此处uid用来指定操作者是谁
    public int updateOrder(int oid, int uid, Map param) {
        Order order = new Order();
        try {
            BeanUtils.populate(order, param);
            if(!OrderStatusCode.contains(order.getStatus()))
                return -1;

            Order oldOrder = orderMapper.getByOid(oid);

            // 操作者是买家
            if (uid == order.getUid()) {
                // placed -> paid, 同时进行的还有改地址
                if (oldOrder.getStatus().equals(OrderStatusCode.PLACED.getStatus())) {
                    if (order.getStatus().equals(OrderStatusCode.PAID.getStatus())) {
                        if (payOrder(order) == -1) { // 钱没够，先充钱
                            return -4;
                        }
                    }
                    else return -2;  // placed 只能转到paid, 否则返回错误代码
                }
                // shipped -> received
                else if (oldOrder.getStatus().equals(OrderStatusCode.SHIPPED.getStatus())) {
                    if (order.getStatus().equals(OrderStatusCode.RECEIVED.getStatus())) {
                        // do nothing
                    }
                    else return -2;  // shipped 只能转到received, 否则返回错误代码
                }
                else
                    return -3;  // buyer 不允许进行其他操作了
            }
            // 操作者是卖家
            else if (uid == oldOrder.getProductPreview().getUid()) {
                // paid -> shipped
                if (oldOrder.getStatus().equals(OrderStatusCode.PAID.getStatus())) {
                    if (order.getStatus().equals(OrderStatusCode.SHIPPED.getStatus())) {
                        // do nothing
                    }
                    else return -2;  // paid 只能转到shipped, 否则返回错误代码
                }
                else
                    return -3;  // seller 不允许进行其他操作了
            }
            // 操作者既不是买家也不是卖家
            else {
                return -3;  // 不允许进行操作
            }
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

    private int payOrder(Order order) {
        // 扣钱
        UserInfo userInfo = userMapper.getUserInfoByUid(order.getUid());
        if (userInfo.getBalance() < order.getPayment())
            return -1;
        userInfo.setBalance(userInfo.getBalance() - order.getPayment());
        return userMapper.updateUserInfo(userInfo);
    }
}
