package com.example.alwayswin.mapper;

/**
 * @ClassName: AddressMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

import com.example.alwayswin.entity.Address;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMapper {
    @Select("Select * from address where aid=#{aid}")
    Address getByAid(int aid);

    @Select("Select * from address where uid=#{uid}")
    List<Address> getByUid(int uid);

    @Options(useGeneratedKeys = true,keyProperty = "aid")
    @Insert("insert into " +
            "address(uid, name,phone, location, state, zipcode) " +
            "values(#{uid}, #{name},#{phone}, #{location}, #{state}, #{zipCode})")
    int add(Address address);

    @Update("update address set " +
            "address.name = #{name}," +
            "address.phone = #{phone}," +
            "address.location = #{location}," +
            "address.state = #{state}," +
            "address.zipcode = #{zipCode}" +
            "where address.aid=#{aid}")
    int update(Address address);

    @Delete("delete from address where aid=#{aid}")
    int delete(int aid);
}
