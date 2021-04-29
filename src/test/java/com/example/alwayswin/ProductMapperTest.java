package com.example.alwayswin;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;

@SpringBootTest
public class ProductMapperTest {
    @Autowired
    ProductMapper productMapper;


    @Test
    public void testMapper(){
        Product product = new Product();
        product = productMapper.getByPidWithStatusAndFigure(1);
        System.out.println(product);
        //System.out.println(productMapper.getProductStatusByPid(1));
    }

}
