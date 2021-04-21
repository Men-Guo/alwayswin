package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Bidding;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: BiddingMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface BiddingMapper {

    @Select("SELECT * from bidding where bid = #{bid}")
    @Results({
            @Result(property = "product",column = "pid", one=@One(select = "com.alwayswin.mapper.ProductMapper.getByPid"))
    })
    Bidding getByBid(int bid);

    @Select("SELECT * from bidding where uid = #{uid}")
    @Results({
            @Result(property = "product",column = "pid", one=@One(select = "com.alwayswin.mapper.ProductMapper.getByPid"))
    })
    List<Bidding> getByUid(int uid);

    @Select("SELECT * from bidding where pid = #{pid}")
    @Results({
            @Result(property = "user",column = "uid", one=@One(select = "com.alwayswin.mapper.UserMapper.getByUid"))
    })
    List<Bidding> getByPid(int pid);

    @Options(useGeneratedKeys = true,keyProperty = "bid")
    @Insert("insert into " +
            "bidding(uid, pid, offer, create_time) " +
            "values(#{uid}, #{pid},#{offer}, #{createTime})")
    int add(Bidding bidding);

    @Update("update bidding set " +
            "bidding.uid = #{uid}," +
            "bidding.pid = #{pid}," +
            "bidding.offer = #{offer}," +
            "bidding.create_time = #{createTime}" +
            "where bidding.bid=#{bid}")
    int update(Bidding bidding);

    @Delete("delete from bidding where bid=#{bid}")
    int delete(int bid);
}
