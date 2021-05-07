package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.service.impl.ProductServiceImpl;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
            if (productService.deleteProduct(pid)!=1){
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
    @PostMapping(value = "/product/post")
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
     * 添加product 同时添加figure和productStatus 添加了回滚 完成
     */
    @PostMapping(value = "/product/create")
    public CommonResult<Object> createProduct(Product product){
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
     * 返回所有overview完成
     */
    @RequestMapping(value = "/product/overviews", method = RequestMethod.GET)
    public CommonResult<List<ProductPreview>> productOverview(){
        List<ProductPreview> productPreviewList = productService.displayAllProduct();
        try{
            if (productPreviewList.isEmpty()){
                logger.warn("Database is empty or error to cross database.");
                return CommonResult.failure();
            }
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(productPreviewList);
    }

    /**
     *  返回所有filter后的商品 完成
     */
    @RequestMapping(value = "/product/{filter}-{sorted}", method = RequestMethod.GET)
    public CommonResult<List<ProductPreview>> productOverviewFilter(@PathVariable("filter") String filter,
                                              @PathVariable("sorted") String sorted){
        List<ProductPreview> productPreviewList = productService.displayAllProductWithFilter(filter,sorted);
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
    }

    /**
     * 根据uid返回商品preview 完成
     */
    @GetMapping(value = "/product/uid/{uid}")
    public CommonResult<List<ProductPreview>> productByUid(@PathVariable("uid") int uid){
        List<ProductPreview> productPreviewList = productService.displayAllProductsByUid(uid);
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
    }

    /**
     * 根据商品cate返回preview 完成

     */
    @GetMapping(value = "/product/category/{cate}")
    public CommonResult<List<ProductPreview>> productByUid(@PathVariable("cate") String cate){
        List<ProductPreview> productPreviewList = productService.displayAllProductsByCate(cate);
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
    }

    /**
     * 更新productStatus完成
     */
    @PostMapping(value = "/productStatus/post")
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
