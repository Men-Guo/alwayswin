package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.*;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.service.ProductService;
import com.example.alwayswin.utils.enums.ProductCateCode;
import com.example.alwayswin.utils.enums.ProductStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private  FigureMapper figureMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 返回一个商品类 完成
     */
    @Override
    public Product displayProductDetail(Integer pid) {
        return productMapper.getByPidWithStatusAndFigure(pid);
    }

    /**
     * 取消 完成
     */
    @Transactional
    @Override
    public Integer cancelProduct(Integer pid) {
        try{
            Product product = productMapper.getByPidWithStatusAndFigure(pid);
            if ( null == product){
                logger.debug("No product with the pid =" + pid);
                return null;
            }

            if (product.isCanceled()) {
                logger.debug("The product already canceled.");
                return null;
            }
            if (!ProductStatusCode.isCancelable(product.getProductStatus().getStatus())){
                logger.debug("The product is not cancelable.");
                return null;
            }
            ProductStatus productStatus = product.getProductStatus();
            if (null == productStatus){
                logger.debug("No product stated, must have some error here.");
                return null;
            }
            product.setCanceled(true);
            productStatus.setStatus("canceled");
            if ((productMapper.updateProductStatus(productStatus)==1)
                    && (productMapper.update(product)==1)){
                return 1;
            }else{
                throw new Exception("Can write into database.");
            }
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
            Product oldProduct = productMapper.getByPid(product.getPid());
            if (null == oldProduct){
                logger.debug("The product doesn't exist.");
                return null;
            }
            if (!ProductCateCode.contains(product.getCate1())){
                logger.debug("The product cate is illegal.");
                return null;
            }
            return productMapper.update(product);
        } catch(Exception e){
          logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 在product和product_status中添加entry
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer createProduct(Product product) {
        try{
            if (product.getAutoWinPrice()==0){
                logger.debug("The AutoWin price is 0, can't create the product.");
                return null;
            }
            if (!ProductCateCode.contains(product.getCate1())){
                logger.debug("Cate is illegal.");
                return null;
            }
            product.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (product.getStartTime().compareTo(new Timestamp(System.currentTimeMillis()))==0){
                product.setStartTime(new Timestamp(System.currentTimeMillis()+24*3600*1000L));
            }
            product.setPassed(false);
            product.setCanceled(false);
            if (product.getEndTime().compareTo(product.getStartTime())==0){
               product.setEndTime(new Timestamp(System.currentTimeMillis()+7*24*3600*1000L));
            }
            int num = productMapper.add(product);
            if (num == 0) throw new Exception("Product add failed");
            ProductStatus productStatus = new ProductStatus();
            productStatus.setPid(product.getPid());
            productStatus.setPrice(product.getStartPrice());
            productStatus.setStatus("pending");
            productStatus.setEndTime(product.getEndTime());
            num = productMapper.addProductStatus(productStatus);
            if (num==0) throw new Exception("Failed to add product status.");
            Figure figure = new Figure();
            figure.setPid(product.getPid());
            figure.setThumbnail(true);
            figure.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
            figure.setDescription("default picture.");
            figure.setUrl("https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png");
            num = figureMapper.add(figure);
            if (num==0) throw new Exception("Failed to add product figure. Gonna reroll.");
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
     * 根据定义的排序展现products
     */
    @Override
    public List<ProductPreview> displayAllProductSorted(String sortedBy, String ordering) {
        try{
            if (!(ordering.toLowerCase().equals("asc")
                    || ordering.toLowerCase().equals("desc"))) {
                logger.debug("The ordering string has typo in " + ordering);
                return null;
            }
            if (!(sortedBy.equals("auto_win_price")
                    || sortedBy.equals("autoWinPrice")
                    || sortedBy.equals("price"))) {
                logger.debug("The sortedBy string has typo in " + sortedBy);
                return null;
            }
            return productMapper.getOrderedPreviewProducts(sortedBy, ordering);
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
        User user = userMapper.getByUid(uid);
        if (null == user){
            logger.debug("User is not exist.");
            return null;
        }
        return productMapper.getByUid(uid);
    }

    /**
     * 根据 Category返回商品
     */
    @Override
    public List<ProductPreview> displayAllProductsByCate(String cate) {
        try {
            if (!ProductCateCode.contains(cate)) {
                logger.debug("The category is not in the category list, double check.");
                return null;
            }
            return productMapper.getByCate1(cate);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 根据 Category和顺序 返回商品
     */
    @Override
    public List<ProductPreview> displayProductsByCateAndSorted(String cate, String sortedBy, String ordering) {
        try{
            if (!(ordering.toLowerCase().equals("asc") || ordering.toLowerCase().equals("desc"))) {
                logger.debug("The ordering string has typo in " + ordering);
                return null;
            }
            else if (!(sortedBy.equals("auto_win_price") || sortedBy.equals("autoWinPrice") || sortedBy.equals("price"))) {
                logger.debug("The sortedBy string has typo in " + sortedBy);
                return null;
            }
            else if (!ProductCateCode.contains(cate)) {
                logger.debug("The category is not in the category list, double check.");
                return null;
            }
            return productMapper.getOrderedPreviewProductsByCate1(cate, sortedBy, ordering);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }


    /**
     * 更新ProductStatus 待完善
     * 需要加判断 例如bidding之后不能更改成pending/waiting等
     */
    @Override
    public Integer updateProductStatusService(ProductStatus productStatus) {
        try{
            if (!ProductStatusCode.contains(productStatus.getStatus())){
                logger.debug("ProductStatus mistakes.");
                return null;
            }
            return productMapper.updateProductStatus(productStatus);
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }

/*    *//**
     * 添加ProductStatus 待完善
     * 需要添加end_time判断
     *//*
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
            if (!ProductStatusCode.contains(productStatus.getStatus())){
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
    }*/

    /**
     * 根据pid展现product status 完成
     */
    @Override
    public ProductStatus displayProductStatus(Integer pid) {
        return productMapper.getProductStatusByPid(pid);
    }








    /////////    仅限测试时使用       //////////////
    //删除的话要进行联排删除, 太麻烦了 暂时不写
/*    @Transactional(rollbackFor = Exception.class)
    public int deleteProduct(Integer pid) {
        try{
            if (productMapper.deleteProduct(pid);)
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
        return 0;
    }*/

}
