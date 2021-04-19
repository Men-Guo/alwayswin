package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Product;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ProductMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface ProductMapper {
    @Select("select * from product where pid = #{pid}")
    Product getByPid(int pid);

    //todo
}
