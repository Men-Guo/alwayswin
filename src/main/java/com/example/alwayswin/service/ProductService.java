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
     * @param pid
     * @return
     */
    Product displayProductDetail(Integer pid);

    /**
     * 删除Product和其FK表内的数据
     * @param pid
     * @return
     */
    Integer deleteProduct(Integer pid);

    /**
     * 更新product表
     * @param product
     * @return
     */
    Integer updateProduct(Product product);

    /**
     * 插入product表
     * @param product
     * @return
     */
    Integer createProduct(Product product);

    /**
     * 根据数据库顺序展示products
     * @return
     */
    List<ProductPreview> displayAllProduct();

    /**
     * 根据filter展现数据库
     * @param filter
     * @param sorted
     * @return
     */
    List<ProductPreview> displayAllProductWithFilter(String filter, String sorted);

    /**
     * 返回某个用户所有卖的商品
     * @param uid
     * @return
     */
    List<ProductPreview> displayAllProductsByUid(Integer uid);

    /**
     * 返回某个cate下的所有商品
     * @param cate
     * @return
     */
    List<ProductPreview> displayAllProductsByCate(String cate);

    /**
     * 删除某个商品的当前状态
     * @param pid
     * @return
     */
    Integer deleteProductStatusService(Integer pid);

    /**
     * 更新某个商品当前状态
     * @param productStatus
     * @return
     */
    Integer updateProductStatusService(ProductStatus productStatus);

    /**
     * 添加productstatus
     * @param productStatus
     * @return
     */
    Integer addProductStatusService(ProductStatus productStatus);

    ProductStatus displayProductStatus(Integer pid);
}
