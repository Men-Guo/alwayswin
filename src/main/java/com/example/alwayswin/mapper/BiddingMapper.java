package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Bidding;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BiddingMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface BiddingMapper {

    @Select("SELECT * from bidding where bid = #{bid}")
    Bidding getByBid(int bid);

    @Select("SELECT * from bidding where uid = #{uid}")
    Bidding getByUid(int uid);

    @Select("SELECT * from bidding where pid = #{pid}")
    Bidding getByPid(int pid);

    @Select("SELECT * from bidding where uid = #{uid} and pid = #{pid}")
    Bidding getByUidAndPid(int uid, int pid);

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
