package com.example.alwayswin.mapper;
import com.example.alwayswin.entity.WishList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WishListMapper {
    @Select("select * from wishlist where uid = #{uid}")
    List<WishList> getByUid(int uid);

    @Options(useGeneratedKeys = true,keyProperty = "wid")
    @Insert("insert into " +
            "wishlist(uid, pid,create_time) " +
            "values(#{uid}, #{pid},#{createTime})")
    int insertWishList(WishList wishList);

    @Delete("delete from wishlist where uid=#{uid} and pid =#{pid}")
    int deleteWishList(int uid, int pid);

    @Delete("delete from wishlist where wid=#{wid}")
    int deleteWishListByWid(int wid);

    @Select("Select * from wishlist where wid=#{wid}")
    WishList selectWid(int wid);

    @Select("Select count(*) from wishlist where pid=#{pid} and uid=#{uid}")
    Integer checkDuplicate(int pid, int uid);
    /*@Update()*/
}
