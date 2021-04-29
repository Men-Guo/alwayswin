package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.service.ProductService;
import org.apache.commons.collections.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FigureMapper figureMapper;

    /**
     * 返回一个商品类
     * @param pid
     * @return
     */
    @Override
    public Product displayProductDetail(Integer pid) {
        return productMapper.getByPidWithStatusAndFigure(pid);
    }

    /**
     * 删除
     * @param pid
     * @return
     */
    @Override
    public Integer deleteProduct(Integer pid) {
        if (productMapper.checkProduct(pid)==0) return 0;
        int num = productMapper.deleteFK(pid);
        return productMapper.delete(pid);
    }

    /**
     * 更新
     * @param product
     * @return
     */
    @Override
    public Integer updateProduct(Product product) {
        if (productMapper.checkProduct(product.getPid())==0) return 0;
        Product oldProduct = productMapper.getByPid(product.getPid());
        product.setUid(oldProduct.getUid());
        product.setCreateTime(oldProduct.getCreateTime());
        return productMapper.update(product);
    }

    /**
     * 创造
     * @param product
     * @return
     */
    @Override
    public Integer createProduct(Product product) {
        Integer num = productMapper.add(product);
        if (num==0) return 0;
        List<Figure> figures= product.getFigures();
        for (Figure figure : figures){
            figure.setPid(product.getPid());
            num = figureMapper.add(figure);
        }
        ProductStatus productStatus = product.getProductStatus();
        productStatus.setPid(product.getPid());
        num = productMapper.addProductStatus(productStatus);
        if (num==0) return 0;
        return 1;
    }

    /**
     * 总览（完成）
     * @return
     */
    @Override
    public List<ProductPreview> displayAllProduct() {
        return productMapper.getPreviewProducts();
    }

    /**
     * 展示全部伴随filter
     * @param filter
     * @param sorted
     * @return
     */
    @Override
    public List<ProductPreview> displayAllProductWithFilter(String filter, String sorted) {
        List<ProductPreview> productPreviews = new ArrayList<>();
        if (sorted != "ASC" || sorted!="DESC") return productPreviews;
        if (filter != "auto_win_price" || filter!="price") return productPreviews;
        productPreviews = productMapper.getFilterPreviewProducts(filter,sorted);
        return productPreviews;
    }

    @Override
    public List<ProductPreview> displayAllProductsByUid(Integer uid) {
        return productMapper.getByUid(uid);
    }

    @Override
    public List<ProductPreview> displayAllProductsByCate(String cate) {
        List<String> cateLists = Arrays.asList("camera","cell phone","accessory","computer"
                ,"tablet","network hardware","tv","smart home","portable audio","car electronics","gaming console"
                ,"vr","others");
        boolean found = false;
        for (String string: cateLists){
            if (string.equals(cate)){
                found = true;
                break;
            }
        }
        if (found) return productMapper.getByCate1(cate);
        else return null;
    }

    @Override
    public Integer deleteProductStatusService(Integer pid) {
        return productMapper.deleteProductStatus(pid);
    }

    @Override
    public Integer updateProductStatusService(ProductStatus productStatus) {
        return productMapper.updateProductStatus(productStatus);
    }

    @Override
    public Integer addProductStatusService(ProductStatus productStatus) {
        int num = productMapper.checkDuplicateProductStatus(productStatus.getPid());
        if (num == 1) return 0;
        return productMapper.addProductStatus(productStatus);
    }

    @Override
    public ProductStatus displayProductStatus(Integer pid) {
        return productMapper.getProductStatusByPid(pid);
    }

}
