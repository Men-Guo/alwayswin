package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Bidding;
import org.apache.ibatis.annotations.Select;
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
}
