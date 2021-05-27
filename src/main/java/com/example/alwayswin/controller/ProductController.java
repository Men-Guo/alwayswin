package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.impl.ProductServiceImpl;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final Logger logger;

    {
        logger = LoggerFactory.getLogger(getClass());
    }


    @Autowired
    ProductServiceImpl productService = new ProductServiceImpl();




    @RequestMapping(value = "/product/search", method = RequestMethod.GET)
    public CommonResult<PageInfo<ProductPreview>> search(@RequestParam("keyword") String keyword,
                                                     @RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){
        try{
            PageHelper.startPage(page,pageSize);
            List<ProductPreview> productPreviews = productService.displaySearchProducts(keyword);
            if (productPreviews==null){
                logger.debug("search does not exists");
                return CommonResult.validateFailure("search does not exists");
            }
            PageInfo<ProductPreview> pageInfo = new PageInfo<>(productPreviews);
            return CommonResult.success(pageInfo);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.failure();
    }
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
    @PutMapping(value = "/product/cancel/{pid}")
    public CommonResult<Object> cancelProduct(@PathVariable("pid") int pid){
        try{
            if (null ==productService.cancelProduct(pid)){
                throw new Exception("Product not exist or can't not be canceled.");
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
    @PutMapping(value = "/product/post")
    public CommonResult<Object> updateProduct(@RequestBody Product product){
        try{
            Integer num = productService.updateProduct(product);
            if (num==0) return CommonResult.failure("The product doesn't exist.");
            if (num==-1) return CommonResult.failure("Already Canceled, can't updated.");
            if (num==-2) return CommonResult.failure("The status shows that can't updated");
            if (num==-3) return CommonResult.failure("The product cate is illegal.");
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(product);
    }

    /**
     * 添加product 同时添加productStatus 添加了回滚 完成
     */
    @PostMapping(value = "/product/create")
    public CommonResult<Object> createProduct(@RequestBody Product product){
        try{
            int res = productService.createProduct(product);
           if (res==-1){
               return CommonResult.failure("The AutoWin price is 0, can't create the product.");
           }
            if (res==-2){
                return CommonResult.failure("Cate is illegal.");
            }
            if (res==-3){
                return CommonResult.failure("failed to insert to databased");
            }
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(product);
    }

    /**
     * 返回product overview
     * 可选择是否排序，是否筛选
     */
    @RequestMapping(value = "/product/overview", method = RequestMethod.GET)
    public CommonResult<PageInfo<ProductPreview>> productOverview(@RequestParam(value = "sortedBy",required = false) String sortedBy,
                                                              @RequestParam(value = "ordering",required = false) String ordering,
                                                              @RequestParam(value = "cate",required = false) String cate,
                                                              @RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                                              @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){
        List<ProductPreview> productPreviewList;
        PageHelper.startPage(page, pageSize);
        if (sortedBy == null || ordering == null) {
            if (cate == null) {
                productPreviewList = productService.displayAllProduct();  // 返回所有商品，默认顺序
            }
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
        PageInfo<ProductPreview> pageInfo = new PageInfo<>(productPreviewList);
        return CommonResult.success(pageInfo);
    }

/*
     *  返回所有filter后的商品 完成
    @RequestMapping(value = "/product/{filter}-{sorted}", method = RequestMethod.GET)
    public CommonResult<List<ProductPreview>> productOverviewFilter(@PathVariable("variable") String variable,
                                              @PathVariable("order") String order){
        List<ProductPreview> productPreviewList = productService.displayAllProductSorted(variable,order);
        try{
            if (productPreviewList.isEmpty()){
                logger.debug("database is empty or fail to connect to database.");
                return CommonResult.failure();
            }
        }
        catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(productPreviewList);
    }*/

    /**
     * 根据uid返回商品preview 完成
     */
    @GetMapping(value = "/user/my-products")
    public CommonResult<List<ProductPreview>> productByUid(@RequestHeader("Authorization") String authHeader,
                                                           @RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
            PageHelper.startPage(page,pageSize);
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
    @PutMapping(value = "/productStatus/post")
    public CommonResult<Object> updateProductStatus(@RequestBody ProductStatus productStatus){
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
