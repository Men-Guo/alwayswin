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
            @Result(property = "pid",column = "pid"),
            @Result(property = "productPreview",column = "pid",
                    one=@One(select = "com.example.alwayswin.mapper.ProductMapper.getProductPreviewByPid")),
    })
    Bidding getByBid(int bid);


    // 某个用户的所有bids
    @Select("SELECT * from bidding where uid = #{uid}")
    @Results({
            @Result(property = "pid",column = "pid"),
            @Result(property = "productPreview",column = "pid",
                    one=@One(select = "com.example.alwayswin.mapper.ProductMapper.getProductPreviewByPid"))
    })
    List<Bidding> getByUid(int uid);


    // 某个product的所有bids
    @Select("SELECT * from bidding where pid = #{pid}")
    @Results({
            @Result(property = "user",column = "uid", one=@One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
    })
    List<Bidding> getByPid(int pid);


    @Options(useGeneratedKeys = true,keyProperty = "bid")
    @Insert("insert into " +
            "bidding(uid, pid, offer, create_time) " +
            "values(#{uid}, #{pid},#{offer}, #{createTime})")
    int add(Bidding bidding);

    // bidding不需要update，出价不能反悔的
//    @Update("update bidding set " +
//            "bidding.uid = #{uid}," +
//            "bidding.pid = #{pid}," +
//            "bidding.offer = #{offer}," +
//            "bidding.create_time = #{createTime}" +
//            "where bidding.bid=#{bid}")
//    int update(Bidding bidding);


    // 仅限 test
    // production中，bidding不需要delete, 也不给你delete
    @Delete("delete from bidding where bid=#{bid}")
    int delete(int bid);
}
