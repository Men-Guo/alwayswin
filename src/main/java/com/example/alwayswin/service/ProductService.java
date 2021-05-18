package com.example.alwayswin.service;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    /**
     * detail页面
     */
    Product displayProductDetail(Integer pid);

    /**
     * 取消product
     */
    Integer cancelProduct(Integer pid);

    /**
     * 更新product表
     */
    Integer updateProduct(Product product);

    /**
     * 插入product表
     */
    Integer createProduct(Product product);

    /**
     * 根据数据库顺序展示products
     */
    List<ProductPreview> displayAllProduct();

    /**
     * 根据定义的排序展现products
     */
    List<ProductPreview> displayAllProductSorted(String column, String ordering);

    /**
     * 返回某个用户所有卖的商品
     */
    List<ProductPreview> displayAllProductsByUid(Integer uid);

    /**
     * 返回某个cate下的所有商品
     */
    List<ProductPreview> displayAllProductsByCate(String cate);

    /**
     * 返回排序后某个cate下的所有商品
     */
    List<ProductPreview> displayProductsByCateAndSorted(String cate, String column, String ordering);

    /**
     * 更新某个商品当前状态
     */
    Integer updateProductStatusService(ProductStatus productStatus);

    /**
     * 废弃
     */
    //Integer addProductStatusService(ProductStatus productStatus);

    ProductStatus displayProductStatus(Integer pid);




    /////////    仅限测试时使用       //////////////
    // 删除product
    //int deleteProduct(Integer pid);
}
