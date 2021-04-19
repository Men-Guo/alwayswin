package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: User
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
@Repository
public interface UserMapper {

    /////////          User          //////////////

    @Select("select * from user where uid = #{uid}")
    User getByUid(int uid);

    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    @Options(useGeneratedKeys = true,keyProperty = "uid")
    @Insert("insert into " +
            "user(username, password, role, status) " +
            "values(#{username}, #{password}, #{role}, #{status})")
    int add(User user);

    // Editing username is not allowed
    @Update("update user set " +
            "user.password = #{password}," +
            "user.role = #{role}," +
            "user.status = #{status}" +
            "where user.uid = #{uid}")
    int update(User user);



    /////////          UserInfo          //////////////

    @Select("select * from user_info where uid = #{uid}")
    UserInfo getUserInfoByUid(int uid);

    @Options(useGeneratedKeys = true,keyProperty = "uiid")
    @Insert("insert into " +
            "user_info(uid, portrait, phone, email, gender, birthday, regis_date, balance) " +
            "values(#{uid}, #{portrait}, #{phone}, #{email}, #{gender}, #{birthday}, #{regisDate}, #{balance})")
    int addUserInfo(UserInfo userInfo);

    @Update("update user_info set " +
            "user_info.uid = #{uid}," +
            "user_info.portrait = #{portrait}," +
            "user_info.phone = #{phone}," +
            "user_info.email = #{email}," +
            "user_info.gender = #{gender}," +
            "user_info.birthday = #{birthday}," +
            "user_info.regis_date = #{regisDate}," +
            "user_info.balance = #{balance}" +
            "where user_info.uiid = #{uiid}")
    int updateUserInfo(UserInfo userInfo);
}
