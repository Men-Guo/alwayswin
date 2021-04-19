package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Figure;
import org.apache.ibatis.annotations.Select;
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
    Figure getByOid(int oid);

    @Select("SELECT * from order where number = #{number}")
    Figure getByNumber(String number);

    @Select("SELECT * from order where uid = #{uid}")
    Figure getByUid(int uid);

    @Select("SELECT * from order where uid = #{uid} and status = #{status}")
    Figure getByUidAndStatus(int uid, String status);
}
