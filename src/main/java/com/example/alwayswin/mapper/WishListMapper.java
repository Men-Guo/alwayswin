package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.WishList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: WishListMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface WishListMapper {
    @Select("select * from wishlist where uid = {#uid}")
    WishList getByUid(int uid);

    @Options(useGeneratedKeys = true,keyProperty = "wid")
    @Insert("insert into " +
            "wishlist(uid, pid,create_time) " +
            "values(#{uid}, #{pid},#{createTime})")
    int add(WishList wishList);

    @Delete("delete from wishlist where wid=#{wid}")
    int delete(int wid);
}