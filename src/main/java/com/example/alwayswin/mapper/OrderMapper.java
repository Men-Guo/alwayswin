package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: OrderMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface OrderMapper {
    @Select("SELECT * from orders where oid = #{oid}")
    Order getByOid(int oid);

    @Select("SELECT * from orders where number = #{number}")
    @Results({
            @Result(property = "product",column = "pid", one=@One(select = "com.alwayswin.mapper.ProductMapper.getByPid"))
    })
    Order getByNumber(String number);

    @Select("SELECT * from orders where uid = #{uid}")
    @Results({
            @Result(property = "product",column = "pid", one=@One(select = "com.alwayswin.mapper.ProductMapper.getByPid"))
    })
    List<Order> getByUid(int uid);

    @Select("SELECT * from orders where uid = #{uid} and status = #{status}")
    @Results({
            @Result(property = "product",column = "pid", one=@One(select = "com.alwayswin.mapper.ProductMapper.getByPid"))
    })
    List<Order> getByUidAndStatus(int uid, String status);

    @Options(useGeneratedKeys = true,keyProperty = "oid")
    @Insert("insert into " +
            "orders(number, uid, pid, aid, payment, create_time, status) " +
            "values(#{number}, #{uid},#{pid}, #{aid}, #{payment}, #{createTime}, #{status})")
    int add(Order order);

    @Update("update orders set " +
            "orders.number = #{number}," +
            "orders.uid = #{uid}," +
            "orders.pid = #{pid}," +
            "orders.aid = #{aid}," +
            "orders.payment = #{payment}," +
            "orders.create_time = #{createTime}," +
            "orders.status = #{status}" +
            "where orders.oid = #{oid}")
    int update(Order order);

    @Delete("delete from orders where oid=#{oid}")
    int delete(int oid);
}
