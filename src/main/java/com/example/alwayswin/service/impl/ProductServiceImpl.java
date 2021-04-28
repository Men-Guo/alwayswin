package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product displayProductDetail(Integer pid) {
        return productMapper.getByPid(pid);
    }

    @Override
    public Integer deleteProduct(Integer pid) {
        if (productMapper.checkProduct(pid)==0) return 0;
        return productMapper.delete(pid);
    }

    @Override
    public Integer updateProduct(Product product) {
        if (productMapper.checkProduct(product.getPid())==0) return 0;
        return productMapper.update(product);
    }

    @Override
    public Integer createProduct(Product product) {
        return productMapper.add(product);
    }

    @Override
    public List<Product> displayAllProduct(String filter, String sorted) {
        return null;
    }
}
