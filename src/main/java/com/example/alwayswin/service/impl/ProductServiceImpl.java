package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FigureMapper figureMapper;

    /**
     * 返回一个商品类 完成
     */
    @Override
    public Product displayProductDetail(Integer pid) {
        return productMapper.getByPidWithStatusAndFigure(pid);
    }

    /**
     * 删除 完成
     */
    @Override
    public Integer deleteProduct(Integer pid) {
        try{
            if (productMapper.checkProduct(pid)==0){
                logger.debug("No product with the pid =" + pid);
                return null;
            }
            Product product = productMapper.getByPid(pid);
            if (product.isCanceled()) {
                logger.debug("The product already canceled.");
                return null;
            }
            product.setCanceled(true);
            return productMapper.update(product);
        }catch (Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }


    /**
     * 更新商品 完成
     */
    @Override
    public Integer updateProduct(Product product) {
        try{
            if (productMapper.checkProduct(product.getPid())==0){
                logger.debug("The product doesn't exist.");
                return null;
            }
            return productMapper.update(product);
        } catch(Exception e){
          logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 增 完成
     */
    @Transactional
    @Override
    public Integer createProduct(Product product) {
        try{
            int num = productMapper.add(product);
            if (num==0) throw new Exception("Product add failed");
            ProductStatus productStatus = product.getProductStatus();
            productStatus.setPid(product.getPid());
            num = productMapper.addProductStatus(productStatus);
            if (num==0) throw new Exception("Failed to add product status.");
            return num;
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 总览 完成
     */
    @Override
    public List<ProductPreview> displayAllProduct() {
        return productMapper.getPreviewProducts();
    }

    /**
     * 展示全部伴随filter 完成
     */
    @Override
    public List<ProductPreview> displayAllProductWithFilter(String filter, String sorted) {
        try{
            if (!(sorted.equals("ASC") || sorted.equals("DESC"))) {
                logger.debug("The sorted string has typo:" + sorted);
                return null;
            }
            if (!(filter.equals("auto_win_price") || filter.equals("price"))) {
                logger.debug("The filter string has typo." + filter);
                return null;
            }
            return productMapper.getFilterPreviewProducts(filter,sorted);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 根据UID返回商品 完成
     */
    @Override
    public List<ProductPreview> displayAllProductsByUid(Integer uid) {
        return productMapper.getByUid(uid);
    }

    /**
     * 根据 Category返回商品 待完善
     * to do filter 和 sorted
     */
    @Override
    public List<ProductPreview> displayAllProductsByCate(String cate) {
        List<String> cateLists = Arrays.asList("camera","cell phone","accessory","computer"
                ,"tablet","network hardware","tv","smart home","portable audio","car electronics","gaming console"
                ,"vr","others");
        boolean found = true;
        try{
            for (String string: cateLists){
                if (string.equals(cate)){
                    found = false;
                    break;
                }
            }
            if (found) {
                logger.debug("The category is not in the category list, double check.");
                return null;
            }
            return productMapper.getByCate1(cate);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 删除ProductStatus 完成
     * 'pending','waiting','bidding','extended','broughtIn','success','canceled'
     */
    @Override
    public Integer deleteProductStatusService(Integer pid) {
        ProductServiceImpl productService = new ProductServiceImpl();
        ProductStatus productStatus = productMapper.getProductStatusByPid(pid);
        try{
            if (productStatus!=null){
                if (productStatus.getStatus().equals("pending") ||productStatus.getStatus().equals("waiting")){
                    productStatus.setStatus("canceled");
                }else{
                    logger.debug("The product status can't change since is not pending or waiting.");
                    return null;
                }
            }else{
                logger.debug("The product status with that pid is not exists.");
                return null;
            }
            return productService.updateProductStatusService(productStatus);
        }
        catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 检查ProductStatus 完成
     */
    private boolean checkProductStatus(String status){
        List<String> statusList = Arrays.asList("pending","waiting","bidding",
                "extended","broughtIn","success","canceled");
        boolean found = true;
        for (String sta: statusList){
            if (sta.equals(status)){
                found= false;
                break;
            }
        }
        return found;
    }

    /**
     * 更新ProductStatus 待完善
     * 需要加判断 例如bidding之后不能更改成pending/waiting等
     */
    @Override
    public Integer updateProductStatusService(ProductStatus productStatus) {
        try{
            boolean found = checkProductStatus(productStatus.getStatus());
            if (found){
                logger.debug("ProductStatus mistakes.");
                return null;
            }
            return productMapper.updateProductStatus(productStatus);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 添加ProductStatus 待完善
     * 需要添加end_time判断
     */
    @Override
    public Integer addProductStatusService(ProductStatus productStatus) {
        try{
            Product product = productMapper.getByPid(productStatus.getPid());
            if (product==null){
                logger.debug("The product pid is not exist.");
                return null;
            }
            int num = productMapper.checkDuplicateProductStatus(productStatus.getPid());
            if (num == 1){
                logger.debug("Product pid already exists.");
                return null;
            }
            if (checkProductStatus(productStatus.getStatus())){
                logger.debug("Product status is illegal");
                return null;
            }
            if (productStatus.getPrice()!=product.getStartPrice()){
                logger.debug("product status start price != start price");
                return null;
            }
            return productMapper.addProductStatus(productStatus);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 根据pid展现product status 完成
     */
    @Override
    public ProductStatus displayProductStatus(Integer pid) {
        return productMapper.getProductStatusByPid(pid);
    }

}
