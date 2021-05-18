package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.entity.UserPreview;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @ClassName: User
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
@Repository
public interface UserMapper {

    /////////          User Preview         //////////////
    @Select("select * from user_preview where uid=#{uid}")
    UserPreview getPreviewByPid(int uid);

    /////////          User          //////////////

    @Select("select * from user where uid = #{uid}")
    User getByUid(int uid);

    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    @Options(useGeneratedKeys = true,keyProperty = "uid")
    @Insert("insert into " +
            "user(username, password, role, status, updated_time) " +
            "values(#{username}, #{password}, #{role}, #{status}, #{updatedTime})")
    int add(User user);

    @Update("update user set " +
            "user.status = #{status}," +
            "user.updated_time = #{updatedTime}" +
            "where user.uid = #{uid}")
    int updateLoginStatus(int uid, boolean status, Timestamp updatedTime);

    @Update("update user set " +
            "user.status = #{status} " +
            "where user.uid = #{uid}")
    int updateLogoutStatus(int uid, boolean status);

    @Update("update user set " +
            "user.password = #{password}" +
            "where user.uid = #{uid}")
    int updatePassword(int uid, String password);

    // for test, userinfo is also deleted
    @Delete("delete from user where uid = #{uid}")
    int deleteUser(int uid);

    /////////          UserInfo          //////////////

    @Select("select * from user_info where uid = #{uid}")
    UserInfo getUserInfoByUid(int uid);


    @Update("update user_info set " +
            "user_info.portrait = #{portrait}," +
            "user_info.phone = #{phone}," +
            "user_info.email = #{email}," +
            "user_info.gender = #{gender}," +
            "user_info.birthday = #{birthday} " +
            "where user_info.uid = #{uid}")
    int updateUserInfo(UserInfo userInfo);


    @Update(("update user_info set user_info.balance = #{balance} where user_info.uid = #{uid}"))
    int updateUserBalance(UserInfo userInfo);
}
