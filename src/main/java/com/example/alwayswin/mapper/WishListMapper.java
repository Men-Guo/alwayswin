package com.example.alwayswin.mapper;
import com.example.alwayswin.entity.WishList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @ClassName: WishListMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface WishListMapper {
    @Select("select * from wishlist where uid = #{uid}")
    List<WishList> getByUid(int uid);

    @Options(useGeneratedKeys = true,keyProperty = "wid")
    @Insert("insert into " +
            "wishlist(uid, pid,create_time) " +
            "values(#{uid}, #{pid},#{createTime})")
    int insertWishList(WishList wishList);

    @Delete("delete from wishlist where wid=#{wid}")
    int deleteWishList(int wid);

    @Select("Select * from wishlist where wid=#{wid}")
    WishList selectWid(int wid);
}
