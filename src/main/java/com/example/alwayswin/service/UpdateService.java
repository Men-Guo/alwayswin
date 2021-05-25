package com.example.alwayswin.service;

import com.example.alwayswin.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateService {
    @Autowired
    ProductService productService = new ProductServiceImpl();

    @Scheduled(initialDelay = 10000, fixedRate = 15000)
    public void timeToCheckProductStatus(){
        productService.updateProductStatusByTime();
    }
}
