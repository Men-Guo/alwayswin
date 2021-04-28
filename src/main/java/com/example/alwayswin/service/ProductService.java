package com.example.alwayswin.service;

import com.example.alwayswin.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product displayProductDetail(Integer pid);
    Integer deleteProduct(Integer pid);
    Integer updateProduct(Integer pid);
    Integer createProduct(Product product);
    List<Product> displayAllProduct();
}
