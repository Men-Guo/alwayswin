package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Order;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.OrderService;
import com.example.alwayswin.service.ProductService;
import com.example.alwayswin.service.impl.ProductServiceImpl;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import com.example.alwayswin.utils.enumUtil.ResultCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderController
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-29
 */

@RestController
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseBody
    @GetMapping("/order/oid/{oid}")
    CommonResult<Order> getByOid(@PathVariable int oid){
        Order order = orderService.getOrderByOid(oid);
        if (order == null) {
            return CommonResult.validateFailure();
        }
        return CommonResult.success(order);
    }

    @ResponseBody
    @GetMapping("/order/number/{number}")
    CommonResult<Order> getByNumber(@PathVariable String number){
        Order order = orderService.getOrderByNumber(number);
        if (order == null) {
            return CommonResult.failure("Oops, there is no such order");
        }
        return CommonResult.success(order);
    }

    @ResponseBody
    @GetMapping("/order/my-order")
    CommonResult<PageInfo<Order>> getByUid(@RequestHeader("Authorization") String authHeader,
                                       @RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
            PageHelper.startPage(page,pageSize);
            List<Order> orderList = orderService.getOrdersByUid(uid);
            if (orderList == null) {
                return CommonResult.failure();
            }
            else {
                PageInfo<Order> pageInfo = new PageInfo<>(orderList);
                return CommonResult.success(pageInfo);
            }
        }
    }

    // 测试用，实际情况下用户无法自行创建order
    @ResponseBody
    @PostMapping("/order/create")
    CommonResult<Object> createOrder(@RequestBody Map param) {
        // 前端记得传过来的address是string
        int res = orderService.addOrder(param);
        if (res == 1) {
            logger.info("Order create successfully");
            return CommonResult.success(res);
        } else {
            logger.debug("Create order failed");
            return CommonResult.failure();
        }
    }

    @ResponseBody
    @PutMapping("/order/update/{oid}")
    CommonResult<Object>  updateOrder(@RequestHeader("Authorization") String authHeader,
                             @RequestBody Map param, @PathVariable int oid){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        int uid = Integer.parseInt(claims.getAudience());

        int res = orderService.updateOrder(oid, uid, param);  // 此处uid指的是操作者，不是订单的拥有者
        if (res == 0) {
            logger.debug("Update order failed");
            return CommonResult.failure();
        }
        else if (res == -1) {
            logger.debug("Wrong order status code");
            return CommonResult.failure("Wrong order status code");
        }
        else if (res == -2) {
            logger.debug("You can't update the order to this status");
            return CommonResult.failure("You can't update the order to this status");
        }
        else if (res == -3) {
            logger.debug("Permission denied");
            return CommonResult.validateFailure(ResultCode.FORBIDDEN);
        }
        else if (res == -4) {
            // 此处前端跳转到充值页面
            logger.debug("Oops, you don't have enough money for this order, please add funds first");
            return CommonResult.validateFailure("Oops, you don't have enough money for this order, please add funds first");
        }
        else {
            logger.info("Order update successfully");
            return CommonResult.success(res);
        }
    }

    @ResponseBody
    @DeleteMapping("/order/delete/{oid}")
    CommonResult<Object>  deleteOrder(@PathVariable int oid){
        int res = orderService.deleteOrder(oid);
        if (res == 1) {
            logger.info("Delete order successfully");
            return CommonResult.success(res);
        }
        else if (res == -1) {
            logger.debug("Can't delete an unfinished order");
            return CommonResult.validateFailure("This order is in process, you can't delete it");
        }
        else {
            logger.debug("Delete order failed");
            return CommonResult.failure();
        }
    }
}
