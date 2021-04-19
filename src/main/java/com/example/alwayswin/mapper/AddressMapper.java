package com.example.alwayswin.mapper;

/**
 * @ClassName: AddressMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

import com.example.alwayswin.entity.Address;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressMapper {
    @Select("Select * from address where aid=#{aid}")
    Address getByAid(int aid);

    @Select("Select * from address where uid=#{uid}")
    Address getByUid(int uid);
}
