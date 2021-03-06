package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductMapper {

    /////////          Product  Preview        //////////////
    /**
     * 不排序返回preview
     */
    @Select("SELECT * from product_preview where status = 'bidding' or status ='waiting' or status like 'extended%'")
    @Results(
            value ={
                    @Result(property = "uid", column = "uid"),
                    @Result(property = "userPreview", column = "uid",
                            one = @One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
            })
    List<ProductPreview> getPreviewProducts();

    @Select("SELECT * from product_preview where title Like #{keyword} and status = 'bidding' or status ='waiting'")
    @Results(
            value ={
                    @Result(property = "uid", column = "uid"),
                    @Result(property = "userPreview", column = "uid",
                            one = @One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
            })
    List<ProductPreview> getPreviewProductsSearch(String keyword);
    /**
     * 根据排序返回list preview
     */
    @Select("SELECT * from product_preview where status = 'bidding' or status ='waiting' or status like 'extended%' order by #{column} #{ordering}")
    @Results(
            value ={
                    @Result(property = "uid", column = "uid"),
                    @Result(property = "userPreview", column = "uid",
                            one = @One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
            })
    List<ProductPreview> getOrderedPreviewProducts(String column, String ordering);

    @Select("SELECT * from product_preview where uid =#{uid}")
    @Results(
            value ={
                    @Result(property = "uid", column = "uid"),
                    @Result(property = "userPreview", column = "uid",
                            one = @One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
            })
    List<ProductPreview> getByUid(int uid);

    @Select("SELECT * from product_preview where cate_1 =#{cate1} and status = 'bidding' or status ='waiting' or status like 'extended%'")
    @Results(
            value ={
                    @Result(property = "uid", column = "uid"),
                    @Result(property = "userPreview", column = "uid",
                            one = @One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
            })
    List<ProductPreview> getByCate1(String cate1);

    @Select("SELECT * from product_preview where cate_1 =#{cate1} and status = 'bidding' or status ='waiting' or status like 'extended%' " +
            "order by #{column} #{ordering}")
    @Results(
            value ={
                    @Result(property = "uid", column = "uid"),
                    @Result(property = "userPreview", column = "uid",
                            one = @One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
            })
    List<ProductPreview> getOrderedPreviewProductsByCate1(String cate1, String column, String ordering);

    @Select("SELECT * from product_preview where pid =#{pid}")
    @Results(
            value ={
                    @Result(property = "uid", column = "uid"),
                    @Result(property = "userPreview", column = "uid",
                            one = @One(select = "com.example.alwayswin.mapper.UserMapper.getPreviewByUid"))
            })
    ProductPreview getProductPreviewByPid(int pid);


    /////////          Product          //////////////

    @Select("select count(*) from product where pid =#{pid}")
    int checkProduct(int pid);

    @Select("select * from product where pid = #{pid}")
    Product getByPid(int pid);

    @Select("select * from product where pid = #{pid}")
    @Results(id =  "productEntityMap",
            value ={
            @Result(property = "pid", column = "pid"),
            @Result(property = "productStatus", column = "pid",
                    one = @One(select = "com.example.alwayswin.mapper.ProductMapper.getProductStatusByPid")),
            @Result(property = "figures", column = "pid",
                    many = @Many(select = "com.example.alwayswin.mapper.FigureMapper.getByPid")),
            @Result(property = "thumbnail", column = "pid",
                    one = @One(select = "com.example.alwayswin.mapper.FigureMapper.getThumbnailByPid"))
    })
    Product getByPidWithStatusAndFigure(int pid);

//    使用搜索引擎, 在searchService实现
//    @Select("select * from product where title like CONCAT('%',#{title},'%') ")
//    List<Product> getByTitle(String title);
    
    @Options(useGeneratedKeys = true,keyProperty = "pid")
    @Insert("insert into " +
            "product(uid, title, description, cate_1, cate_2, cate_3, create_time, start_time, end_time, " +
            "start_price, auto_win_price, reserved_price, min_increment, is_passed, is_canceled) " +
            "values(#{uid}, #{title}, #{description}, #{cate1}, #{cate2}, #{cate3}, #{createTime}, #{startTime}, #{endTime}," +
            "#{startPrice}, #{autoWinPrice}, #{reservedPrice}, #{minIncrement}, #{isPassed}, #{isCanceled})")
    int add(Product product);

    // uid, createTime are NOT allowed to be modified
    @Update("update product set " +
            "product.title = #{title}," +
            "product.description = #{description}," +
            "product.cate_1 = #{cate1}," +
            "product.cate_2 = #{cate2}," +
            "product.cate_3 = #{cate3}," +
            "product.start_time = #{startTime}," +
            "product.end_time = #{endTime}," +
            "product.start_price = #{startPrice}," +
            "product.auto_win_price = #{autoWinPrice}," +
            "product.reserved_price = #{reservedPrice}," +
            "product.min_increment = #{minIncrement}," +
            "product.is_passed = #{isPassed}, " +
            "product.is_canceled = #{isCanceled}" +
            " where product.pid = #{pid}")
    int update(Product product);
    
    /////////          Product Status          //////////////

    @Select("select * from product_status where pid = #{pid}")
    ProductStatus getProductStatusByPid(int pid);

    @Options(useGeneratedKeys = true,keyProperty = "psid")
    @Insert("insert into " +
            "product_status(pid, price, status, end_time) " +
            "values(#{pid}, #{price}, #{status}, #{endTime})")
    int addProductStatus(ProductStatus productStatus);

    @Select("select count(*) from product_status where pid =#{pid}")
    int checkDuplicateProductStatus(int pid);

    @Update("update product_status set " +
            "product_status.price = #{price}," +
            "product_status.end_time = #{endTime}," +
            "product_status.status = #{status}" +
            "where product_status.pid = #{pid}")
    int updateProductStatus(ProductStatus productStatus);

    @Delete("delete from product_status where pid=#{pid}")
    int deleteProductStatus(int pid);

    @Select("SELECT * FROM product_status where end_time < (select now() - INTERVAL 25200 SECOND) and (product_status.`status`!=\"success\" and product_status.`status`!=\"broughtIn\")")
    List<ProductStatus> getDueProduct();

    @Select("SELECT product_status.* FROM product_status join product on product.pid = product_status.pid where product.start_time  BETWEEN (select now()- INTERVAL 25215 SECOND) and (select now()- INTERVAL 25200 SECOND) and (product_status.`status`=\"waiting\")")
    List<ProductStatus> getWaitingProduct();
    /////////    仅限测试时使用       //////////////
    @Delete("DELETE FROM product WHERE pid = #{pid}")
    int deleteProduct(int pid);
}
