package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: OrderMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface OrderMapper {
    @Select("SELECT * from order where oid = #{oid}")
    Order getByOid(int oid);

    @Select("SELECT * from order where number = #{number}")
    Order getByNumber(String number);

    @Select("SELECT * from order where uid = #{uid}")
    Order getByUid(int uid);

    @Select("SELECT * from order where uid = #{uid} and status = #{status}")
    Order getByUidAndStatus(int uid, String status);

    @Options(useGeneratedKeys = true,keyProperty = "oid")
    @Insert("insert into " +
            "order(number, uid, pid, aid, payment, create_time, status) " +
            "values(#{number}, #{uid},#{pid}, #{aid}, #{payment}, #{createTime}, #{status})")
    int add(Order order);

    @Update("update order set " +
            "order.number = #{number}," +
            "order.uid = #{uid}," +
            "order.pid = #{pid}," +
            "order.aid = #{aid}," +
            "order.payment = #{payment}," +
            "order.create_time = #{createTime}," +
            "order.status = #{status}" +
            "where order.oid = #{oid}")
    int update(Order order);

    @Delete("delete from order where oid=#{oid}")
    int delete(int oid);
}
