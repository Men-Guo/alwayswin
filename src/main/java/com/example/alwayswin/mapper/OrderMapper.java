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
    @Results({
            @Result(property = "productPreview",column = "pid", one=@One(select = "com.example.alwayswin.mapper.ProductMapper.getProductPreviewByPid"))
    })
    Order getByOid(int oid);

    @Select("SELECT * from orders where number = #{number}")
    @Results({
            @Result(property = "productPreview",column = "pid", one=@One(select = "com.example.alwayswin.mapper.ProductMapper.getProductPreviewByPid"))
    })
    Order getByNumber(String number);

    @Select("SELECT * from orders where uid = #{uid}")
    @Results({
            @Result(property = "productPreview",column = "pid", one=@One(select = "com.example.alwayswin.mapper.ProductMapper.getProductPreviewByPid"))
    })
    List<Order> getByUid(int uid);

    // 哎前端做吧
//    @Select("SELECT * from orders where uid = #{uid} and status = #{status}")
//    @Results({
//            @Result(property = "product",column = "pid", one=@One(select = "com.example.alwayswin.mapper.ProductMapper.getByPid"))
//    })
//    List<Order> getByUidAndStatus(int uid, String status);

    @Options(useGeneratedKeys = true,keyProperty = "oid")
    @Insert("insert into " +
            "orders(number, uid, pid, address, payment, create_time, status) " +
            "values(#{number}, #{uid},#{pid}, #{address}, #{payment}, #{createTime}, #{status})")
    int add(Order order);

    // 其他属性不能修改
    @Update("update orders set " +
            "orders.address = #{address}," +
            "orders.payment = #{payment}," +
            "orders.status = #{status}" +
            "where orders.oid = #{oid}")
    int update(Order order);

    @Delete("delete from orders where oid=#{oid}")
    int delete(int oid);
}
