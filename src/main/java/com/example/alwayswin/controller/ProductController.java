package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.impl.ProductServiceImpl;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductServiceImpl productService = new ProductServiceImpl();

    /**
     * product详情展示页面 完成
     */
    @RequestMapping(value = "/product/{pid}", method = RequestMethod.GET)
    public CommonResult<Product> productDetail(@PathVariable("pid") int pid){
        try{
            Product product = productService.displayProductDetail(pid);
            if (product==null){
                logger.debug("Product does not exists");
                return CommonResult.validateFailure("Product does not exists");
            }
            return CommonResult.success(product);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.failure();
    }

    /**
     * cancel某个pid 先更改product status 再更改product, 如不完成进行回滚 完成
     */
    @Transactional
    @PostMapping(value = "/product/cancel/{pid}")
    public CommonResult<Object> cancelProduct(@PathVariable("pid") int pid){
        try{
            if (productService.deleteProductStatusService(pid)!=1){
                throw new Exception("Delete Product Status error.");
            }
            if (productService.cancelProduct(pid)!=1){
                throw new Exception("Delete Product error");
            }
            return CommonResult.success(productService.displayProductDetail(pid));
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.failure();
    }

    /**
     * 更新某个pid
     */
    @PostMapping(value = "/product/update/{pid}")
    public CommonResult<Object> updateProduct(@RequestBody Product product){
        try{
            Integer num = productService.updateProduct(product);
            if (num!=1) return CommonResult.failure();
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(product);
    }

    /**
     * 添加product 同时添加productStatus 添加了回滚 完成
     */
    @PostMapping(value = "/product/create")
    public CommonResult createProduct(Product product){
        try{
           if (productService.createProduct(product)!=1){
               logger.debug("product insert failure.");
               return CommonResult.failure();
           }
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.failure();
    }

    /**
     * 返回product overview
     * 可选择是否排序，是否筛选
     */
    @RequestMapping(value = "/product/overview", method = RequestMethod.GET)
    public CommonResult<List<ProductPreview>> productOverview(@RequestParam(required = false) String sortedBy,
                                                              @RequestParam(required = false) String ordering,
                                                              @RequestParam(required = false) String cate){
        List<ProductPreview> productPreviewList = null;
        if (sortedBy == null || ordering == null) {
            if (cate == null)
                productPreviewList = productService.displayAllProduct();  // 返回所有商品，默认顺序
            else
                productPreviewList = productService.displayAllProductsByCate(cate);  // 返回筛选商品，默认顺序
        }
        else {
            if (cate == null)
                productPreviewList = productService.displayAllProductSorted(sortedBy, ordering);  // 返回所有商品，排序
            else
                productPreviewList = productService.displayProductsByCateAndSorted(cate, sortedBy, ordering);  // 返回筛选商品，排序
        }
        try{
            if (productPreviewList == null){
                logger.warn("Database error when executing productOverview" );
                return CommonResult.failure();
            }
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(productPreviewList);
    }


    /**
     * 根据uid返回商品preview 完成
     */
    @GetMapping(value = "/user/my-products")
    public CommonResult<List<ProductPreview>> productByUid(@RequestHeader("Authorization") String authHeader){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.valueOf(claims.getAudience());
            List<ProductPreview> productPreviewList = productService.displayAllProductsByUid(uid);
            try {
                if (productPreviewList == null) {
                    logger.debug("Database error in productByUid");
                    return CommonResult.failure();
                }
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
            return CommonResult.success(productPreviewList);
        }
    }
    
    /**
     * 更新productStatus完成
     */
    @PostMapping(value = "/productStatus/post")
    public CommonResult updateProductStatus(@RequestBody ProductStatus productStatus){
        try{
            Integer num = productService.updateProductStatusService(productStatus);
            if (num==0) return CommonResult.failure();
        }
        catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(null);
    }

    /**
     * 根据pid查找product status 完成
     */
    @RequestMapping(value = "/productStatus/{pid}", method = RequestMethod.GET)
    public CommonResult<ProductStatus> getProductStatusByPid(@PathVariable("pid") int pid){
        ProductStatus productStatus = productService.displayProductStatus(pid);
        try{
            if (productStatus==null) return CommonResult.failure();
        }
        catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(productStatus);
    }
}
