package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.service.impl.ProductServiceImpl;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductServiceImpl productService = new ProductServiceImpl();

    /**
     * product详情展示页面 完成
     * @param pid
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
     * cancel某个pid 完成
     * @param pid
     */
    @RequestMapping(value = "/product/cancel/{pid}",method = RequestMethod.POST)
    public CommonResult<Object> cancelProduct(@PathVariable("pid") int pid){
        try{
            if (productService.deleteProductStatusService(pid)!=1){
                logger.debug("Product Status update fails.See logger");
                return CommonResult.failure();
            }
            if (productService.deleteProduct(pid)!=1){
                logger.debug("Product update fails, see logger.");
                return CommonResult.failure();
            }
            return CommonResult.success(productService.displayProductDetail(pid));
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(null);
    }

    /**
     * 更新某个pid to do
     * @param pid
     * @return
     */
    @RequestMapping(value = "/product/update/{pid}",method = RequestMethod.POST)
    public CommonResult<Object> updateProduct(@PathVariable("pid") int pid){
        return null;
    }

    /**
     * 添加product 同时添加figure和productStatus 完成
     * @param product
     * @return
     */
    @RequestMapping(value = "/product/create", method = RequestMethod.POST)
    public CommonResult<Object> createProduct(Product product){
        try{
           if (productService.createProduct(product)!=1){
               logger.debug("product insert failure.");
               return CommonResult.failure();
           }
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return CommonResult.success(null);
    }

    /**
     * 返回所有overview完成
     * @return
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
     * @param filter
     * @param sorted
     * @return
     */
    @RequestMapping(value = "/product/overviews/{filter}-{sorted}", method = RequestMethod.GET)
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
     * @param uid
     * @return
     */
    @RequestMapping(value = "/product/uid/{uid}",method = RequestMethod.GET)
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
     * @param cate
     * @return
     */
    @RequestMapping(value = "/product/category/{cate}",method = RequestMethod.GET)
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
     * 待完成
     * @return
     */
    @RequestMapping(value = "/productStatus/{pid}",method = RequestMethod.POST)
    public CommonResult<Object> updateProductStatus(){
        return null;
    }

    /**
     * 根据pid查找product status 完成
     * @param pid
     * @return
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
