package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ProductMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface ProductMapper {
    
    //todo: filter and sorted

    /**
     * 不排序返回preview
     * @return
     */
    @Select("SELECT product.pid, product.uid,product.title,product.end_time,product.auto_win_price,"
            + "product_status.price,product_status.`status`, figure.url "
            + "FROM ((product INNER JOIN product_status on product.pid = product_status.pid)"
            + "INNER JOIN figure on product.pid = figure.pid)"
            + "where figure.is_thumbnail=1")
    List<ProductPreview> getPreviewProducts();

    /**
     * 根据filter排序返回list preview
     * @param filter
     * @param sorted
     * @return
     */
    @Select("SELECT product.pid, product.uid,product.title,product.end_time,product.auto_win_price,"
            + "product_status.price,product_status.`status`, figure.url "
            + "FROM ((product INNER JOIN product_status on product.pid = product_status.pid)"
            + "INNER JOIN figure on product.pid = figure.pid)"
            + "where figure.is_thumbnail=1"
            + "order by #{filter} #{sorted}")
    List<ProductPreview> getFilterPreviewProducts(String filter, String sorted);
    /////////          Product          //////////////

    @Select("select count(*) from product where pid =#{pid}")
    Integer checkProduct(int pid);

    @Select("select * from product where pid = #{pid}")
    Product getByPid(int pid);

    @Select("select * from product where pid = #{pid}")
    @Results(id =  "productEntityMap",
            value ={
            @Result(property = "productStatus", column = "pid",
                    one = @One(select = "com.example.alwayswin.mapper.ProductMapper.getProductStatusByPid")),
            @Result(property = "figures", column = "pid",
                    many = @Many(select = "com.example.alwayswin.mapper.FigureMapper.getByPid")),
            @Result(property = "thumbnail", column = "pid",
                    one = @One(select = "com.example.alwayswin.mapper.FigureMapper.getThumbnailByPid"))
    })
    Product getByPidWithStatusAndFigure(int pid);

    @Select("SELECT product.pid, product.uid,product.title,product.end_time,product.auto_win_price,"
            + "product_status.price,product_status.`status`, figure.url "
            + "FROM ((product INNER JOIN product_status on product.pid = product_status.pid)"
            + "INNER JOIN figure on product.pid = figure.pid)"
            + "where figure.is_thumbnail=1"
            + "and uid =#{uid}")
    List<ProductPreview> getByUid(int uid);
/*    @Select("select * from product where uid = #{uid}")
    List<Product> getByUid(int uid);*/

//    @Select("select * from product where cate1 = #{cate1}")
    @Select("SELECT product.pid, product.uid,product.title,product.end_time,product.auto_win_price,"
        + "product_status.price,product_status.`status`, figure.url "
        + "FROM ((product INNER JOIN product_status on product.pid = product_status.pid)"
        + "INNER JOIN figure on product.pid = figure.pid)"
        + "where figure.is_thumbnail=1"
        + "and cate_1 =#{cate1}")
    List<ProductPreview> getByCate1(String cate1);

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
            "product.uid =#{uid},"+
            "product.title = #{title}," +
            "product.description = #{description}," +
            "product.cate_1 = #{cate1}," +
            "product.cate_2 = #{cate2}," +
            "product.cate_3 = #{cate3}," +
            "product.start_time = #{startTime}," +
            "product.end_time = #{endTime}," +
            "product.create_time = #{createTime}," +
            "product.start_price = #{startPrice}" +
            "product.auto_win_price = #{autoWinPrice}," +
            "product.reserved_price = #{reservedPrice}," +
            "product.min_increment = #{minIncrement}," +
            "product.is_passed = #{isPassed}, " +
            "product.is_canceled = #{isCanceled}" +
            "where product.pid = #{pid}")
    int update(Product product);

    /**
     * 整个product删除用
     * @param pid
     * @return
     */
    @Delete("delete figure, product_status,orders,wishlist from product"
            + "left join figure on product.pid = figure.pid "
            + "left join product_status on product.pid = product_status.pid"
            + "left join orders on orders.pid=product.pid"
            + "left join wishlist on wishlist.pid = product.pid"
            + "where product.pid =#{pid}")
    int deleteFK(int pid);

    @Delete("delete from product where pid=#{pid}")
    int delete(int pid);


    
    /////////          Product Status          //////////////

    @Select("select * from product_status where pid = #{pid}")
    ProductStatus getProductStatusByPid(int pid);

    @Options(useGeneratedKeys = true,keyProperty = "psid")
    @Insert("insert into " +
            "product_status(pid, price, status, end_time) " +
            "values#{pid}, #{price}, #{status}, #{endTime})")
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
}
