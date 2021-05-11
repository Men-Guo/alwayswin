package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ProductMapperTest {
    @Autowired
    ProductMapper productMapper;

    //ProductPreviews
    @Test
    public void happyPathGetProductAndProductPreview(){
        Product product = productMapper.getByPidWithStatusAndFigure(1);
        assertEquals(1,product.getPid());
        assertEquals(1,product.getProductStatus().getPid());
        List<ProductPreview> productPreviews = productMapper.getOrderedPreviewProducts("price","DESC");
        System.out.println(productPreviews);
        productPreviews = productMapper.getOrderedPreviewProducts("price","ASC");
        System.out.println(productPreviews);
        assertEquals(13,productPreviews.size());
    }

    @Test
    public void GetProductAndProductPreviewNoSuchPid(){
        assertNull(productMapper.getByPidWithStatusAndFigure(-1));
    }

    @Test
    public void queryAllProductPreview(){
        List<ProductPreview> productPreviews = productMapper.getPreviewProducts();
        assertEquals(13, productPreviews.size());
    }

    @Test
    public void queryProductPreviewByUid(){
        List<ProductPreview> productPreviews = productMapper.getByUid(1);
        assertEquals(3, productPreviews.size());
    }

    @Test
    public void queryProductPreviewByCate1(){
        List<ProductPreview> productPreviews = productMapper.getByCate1("cell phone");
        assertEquals(7, productPreviews.size());
    }

    @Test
    public void queryProductPreviewByCate1OrderDESC(){
        List<ProductPreview> productPreviews = productMapper.getOrderedPreviewProductsByCate1("cell phone", "price","DESC");
        System.out.println(productPreviews);
        assertEquals(7, productPreviews.size());
    }

    @Test
    public void queryProductPreviewByCate1OrderASC(){
        List<ProductPreview> productPreviews = productMapper.getOrderedPreviewProductsByCate1("cell phone", "price","ASC");
        System.out.println(productPreviews);
        assertEquals(7, productPreviews.size());
    }

    //Product

    @Test
    public void happyPathForQueryPid(){
        assertEquals(1,productMapper.getByPid(1).getPid());
    }

    @Test
    public void checkPid(){
        assertEquals(1, productMapper.checkProduct(1));
        assertEquals(0, productMapper.checkProduct(-1));
    }

    @Test
    public void happyPathGetProductWithStatusAndFigure(){
        Product product = productMapper.getByPidWithStatusAndFigure(1);
        System.out.println( product);
        assertEquals(1,product.getPid());
    }

    // Product Statues

    @Test
    public void happyPathGetProductStatusByPid(){
        assertEquals(1,productMapper.getProductStatusByPid(1).getPid());
    }

    @Test
    public void checkDuplicatedProductStatusByPid(){
        assertEquals(1,productMapper.checkDuplicateProductStatus(1));
    }

    @Test
    public void deletePid(){
        assertEquals(1,productMapper.deleteProductStatus(26));
        assertEquals(1,productMapper.deleteProduct(26));
    }

    @Test
    public void insertProductAndProductStatus(){
        Product product = new Product();
        product.setUid(2);
        product.setTitle("test2");
        product.setCate1("cell phone");
        product.setCreateTime(new Timestamp(System.currentTimeMillis()));
        product.setEndTime(new Timestamp(System.currentTimeMillis()));
        product.setStartTime(new Timestamp(System.currentTimeMillis()));
        product.setPassed(true);
        product.setCanceled(false);
        assertEquals(1, productMapper.add(product));

        ProductStatus productStatus = new ProductStatus();
        productStatus.setPid(product.getPid());
        productStatus.setPrice(product.getStartPrice());
        productStatus.setStatus("pending");
        productStatus.setEndTime(product.getEndTime());
        assertEquals(1, productMapper.addProductStatus(productStatus));
    }

    @Test
    public void update(){
        ProductStatus productStatus = new ProductStatus();
        productStatus.setPid(24);
        productStatus.setPrice(300);
        productStatus.setStatus("bidding");
        productStatus.setEndTime(new Timestamp(System.currentTimeMillis()));
        assertEquals(1, productMapper.updateProductStatus(productStatus));

        Product product = new Product();
        product.setPid(24);
        product.setUid(2);
        product.setTitle("test333");
        product.setCate1("cell phone");
        product.setCreateTime(new Timestamp(System.currentTimeMillis()));
        product.setEndTime(new Timestamp(System.currentTimeMillis()));
        product.setStartTime(new Timestamp(System.currentTimeMillis()));
        product.setPassed(true);
        product.setCanceled(false);
        assertEquals(1, productMapper.update(product));
    }
}
