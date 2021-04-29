package com.example.alwayswin;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<ProductPreview> productPreviews = productMapper.getFilterPreviewProducts("price","DESC");
        System.out.println(productPreviews);
    }

    @Test
    public void testMapper2(){
        //System.out.println(productMapper.getProductStatusByPid(1));
        List<ProductPreview> productPreviews = productMapper.getPreviewProducts();
        System.out.println(productPreviews);
    }

    @Test
    public void testMapper3(){
        Product product = new Product();
        /*product.setPid(14);*/
        product.setUid(1);
        product.setTitle("AA");
        product.setDescription("test");
        product.setCate1("cell phone");
        Figure figure= new Figure();

        Integer num = productMapper.add(product);
        assertEquals(1,num);
        System.out.println(product.getPid());

    }

    @Test
    public void testMapper4(){
        //System.out.println(productMapper.getProductStatusByPid(1));
        assertEquals(1,productMapper.delete(14));
    }
}
