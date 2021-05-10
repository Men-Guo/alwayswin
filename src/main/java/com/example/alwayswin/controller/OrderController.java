package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Order;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.OrderService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @GetMapping("/order/{oid}")
    CommonResult<Order> getByOid(@PathVariable int oid){
        if (oid < 0)
            return CommonResult.validateFailure();

        Order order = orderService.getOrderByOid(oid);
        if (order == null) {
            return CommonResult.validateFailure();
        }
        return CommonResult.success(order);
    }

    @ResponseBody
    @GetMapping("/order/{number}")
    CommonResult<Order> getByNumber(@PathVariable String number){
        Order order = orderService.getOrderByNumber(number);
        if (order == null) {
            return CommonResult.validateFailure();
        }
        return CommonResult.success(order);
    }

    @ResponseBody
    @GetMapping("/order/all")
    CommonResult<List<Order>> getByUid(@RequestHeader("Authorization") String authHeader){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            List<Order> orderList = orderService.getOrdersByUid(uid);
            if (orderList == null) {
                return CommonResult.failure();
            }
            else {
                return CommonResult.success(orderList);
            }
        }
    }

    @ResponseBody
    @PostMapping("/order/create")
    CommonResult createOrder(@RequestBody Map param) {
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
    CommonResult updateOrder(@RequestBody Map param, @PathVariable int oid){
        if (oid < 0)
            return CommonResult.validateFailure();

        int res = orderService.updateOrder(oid, param);
        if (res == 1) {
            logger.info("Update order successfully");
            return CommonResult.success(res);
        }
        else {
            logger.debug("Update order failed");
            return CommonResult.failure();
        }
    }

    @ResponseBody
    @DeleteMapping("/order/delete/{oid}")
    CommonResult deleteOrder(@RequestBody Map param, @PathVariable int oid){
        if (oid < 0)
            return CommonResult.validateFailure();

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
